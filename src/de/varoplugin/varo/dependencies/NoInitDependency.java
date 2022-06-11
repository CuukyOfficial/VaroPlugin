package de.varoplugin.varo.dependencies;

import de.varoplugin.varo.VaroPlugin;

class NoInitDependency extends Dependency {

	NoInitDependency(String name, String link, String hash) {
		super(name, link, hash);
	}

	NoInitDependency(String name, String link, String hash, LoadPolicy loadPolicy) {
		super(name, link, hash, loadPolicy);
	}

	@Override
	protected void init(VaroPlugin varo) {
	}
}
