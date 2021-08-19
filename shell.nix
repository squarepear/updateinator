{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
	name = "java-dev-shell";

	nativeBuildInputs = with pkgs; [
		gradle
		jdk16_headless
	];

	JAVA_HOME = pkgs.jdk16_headless;
}
