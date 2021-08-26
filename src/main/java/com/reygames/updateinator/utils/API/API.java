package com.reygames.updateinator.utils.API;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class API {
	static String BASE_ENDPOINT = "https://api.modrinth.com/api/v1";

	public static APIVersion getLatestVersion(String slug, String mcVersion) throws Exception {
		APIMod mod = getMod(slug);
		
		for (String versionID : mod.versions) {
			APIVersion version = getVersion(versionID);

			if (!version.game_versions.contains(mcVersion) || !version.loaders.contains("fabric")) // Skip if not for correct MC version or not for Fabric
				continue;

			return version;
		}

		throw new Exception("No version matches MC version");
	}

	public static APIMod getMod(String search) throws Exception {
		String slug = Unirest.get(String.format("%s/mod?query=%s", BASE_ENDPOINT, search))
		.header("accept", "application/json")
		.asJson().getBody().getObject()
		.getJSONArray("hits").getJSONObject(0).getString("slug");

		String endpoint = String.format("%s/mod/%s", BASE_ENDPOINT, slug);
		
		HttpResponse<JsonNode> response = Unirest.get(endpoint)
		.header("accept", "application/json")
		.asJson();
		
		if (response.getStatus() != 200)
		throw new Exception("Invalid Mod");
		
		JsonNode body = response.getBody();
		
		APIMod mod = new APIMod();

		mod.id = body.getObject().getString("id");
		mod.slug = body.getObject().getString("slug");
		mod.published = body.getObject().getString("published");
		mod.updated = body.getObject().getString("updated");
		mod.status = body.getObject().getString("status");
		mod.downloads = body.getObject().getInt("downloads");
		mod.followers = body.getObject().getInt("followers");
		mod.versions = body.getObject().getJSONArray("versions").toList();
		mod.source_url = body.getObject().getString("source_url");

		return mod;
	}

	public static APIVersion getVersion(String versionID) throws Exception {
		String endpoint = String.format("%s/version/%s", BASE_ENDPOINT, versionID);
		
		HttpResponse<JsonNode> response = Unirest.get(endpoint)
		.header("accept", "application/json")
		.asJson();
		
		if (response.getStatus() != 200)
		throw new Exception("Invalid Version");
		
		JsonNode body = response.getBody();
		
		APIVersion version = new APIVersion();

		version.id = body.getObject().getString("id");
		version.mod_id = body.getObject().getString("mod_id");
		version.version_number = body.getObject().getString("version_number");
		version.date_published = body.getObject().getString("date_published");
		version.downloads = body.getObject().getInt("downloads");
		version.files = body.getObject().getJSONArray("files").toList();
		version.game_versions = body.getObject().getJSONArray("game_versions").toList();
		version.loaders = body.getObject().getJSONArray("loaders").toList();

		return version;
	}
}
