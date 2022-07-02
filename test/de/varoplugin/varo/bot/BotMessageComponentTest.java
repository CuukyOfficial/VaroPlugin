package de.varoplugin.varo.bot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BotMessageComponentTest {

	@Test
	public void placeHolderTest() {
		BotMessageComponent[] components = BotMessageComponent.splitPlaceholders("abc%def%ghi", false, false, "%def%");
		BotMessageComponent[] replacedComponents = BotMessageComponent.replacePlaceholders(components, new String[] {"%def%"}, new String[] {"xyz"});
		StringBuilder output = new StringBuilder();
		for(BotMessageComponent component : replacedComponents)
			output.append(component.getContent());
		assertEquals("abcxyzghi", output.toString());
	}
	
	@Test
	public void placeHolderTest2() {
		BotMessageComponent[] components = BotMessageComponent.splitPlaceholders("abc%def%ghi%def%jkl", false, false, "%def%");
		BotMessageComponent[] replacedComponents = BotMessageComponent.replacePlaceholders(components, new String[] {"%def%"}, new String[] {"xyz"});
		StringBuilder output = new StringBuilder();
		for(BotMessageComponent component : replacedComponents)
			output.append(component.getContent());
		assertEquals("abcxyzghixyzjkl", output.toString());
	}
	
	@Test
	public void placeHolderTest3() {
		BotMessageComponent[] components = BotMessageComponent.splitPlaceholders("abcde", false, false, "b", "d");
		BotMessageComponent[] replacedComponents = BotMessageComponent.replacePlaceholders(components, new String[] {"b", "d"}, new String[] {"x", "y"});
		StringBuilder output = new StringBuilder();
		for(BotMessageComponent component : replacedComponents)
			output.append(component.getContent());
		assertEquals("axcye", output.toString());
	}
	
	@Test
	public void placeHolderTest4() {
		BotMessageComponent[] components = BotMessageComponent.splitPlaceholders("abcdefg", false, false, "b", "d", "f");
		BotMessageComponent[] replacedComponents = BotMessageComponent.replacePlaceholders(components, new String[] {"b", "d", "f"}, new String[] {"x", "y", "z"});
		StringBuilder output = new StringBuilder();
		for(BotMessageComponent component : replacedComponents)
			output.append(component.getContent());
		assertEquals("axcyezg", output.toString());
	}
	
	@Test
	public void placeHolderTest5() {
		BotMessageComponent[] components = BotMessageComponent.splitPlaceholders("ab", false, false, "b", "d", "f");
		BotMessageComponent[] replacedComponents = BotMessageComponent.replacePlaceholders(components, new String[] {"b", "d", "f"}, new String[] {"x", "y", "z"});
		StringBuilder output = new StringBuilder();
		for(BotMessageComponent component : replacedComponents)
			output.append(component.getContent());
		assertEquals("ax", output.toString());
	}
	
	@Test
	public void placeHolderTest6() {
		BotMessageComponent[] components = BotMessageComponent.splitPlaceholders("b", false, false, "b", "d", "f");
		BotMessageComponent[] replacedComponents = BotMessageComponent.replacePlaceholders(components, new String[] {"b", "d", "f"}, new String[] {"x", "y", "z"});
		StringBuilder output = new StringBuilder();
		for(BotMessageComponent component : replacedComponents)
			output.append(component.getContent());
		assertEquals("x", output.toString());
	}
}
