package cz.muni.fi.pa165.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jan Stourac
 */
public class DefaultPasswordStrengthValidator implements PasswordStrengthValidator {

	public static enum CharacterGroup {
		LETTERS, LOWERCASE_LETTERS, UPPERCASE_LETTERS, SPECIAL_CHARACTERS, NUMBERS;

		public String toReadableString() {
			return toString().replace("_", " ").toLowerCase();
		}
	}

	private static final String ERROR_MIN_LENGTH_TPL = "Minimal length of password is %d characters!";
	private static final String ERROR_MIN_GROUPS_TPL = "Password must consist from at least %d character groups (%s)";
	private static final String ERROR_GROUP_MIN_OCCURENCE_TPL = "Password must constains at least %d %s";

	private int minLength;
	private int minGroupsCount;
	private final Map<CharacterGroup, Integer> groups = new EnumMap(CharacterGroup.class);

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMinGroupsCount() {
		return minGroupsCount;
	}

	public void setMinGroupsCount(int minGroupsCount) {
		this.minGroupsCount = minGroupsCount;
	}

	public void addGroup(CharacterGroup group, int minOccurence) {
		groups.put(group, minOccurence);
	}

	public Map<CharacterGroup, Integer> getGroups() {
		return Collections.unmodifiableMap(groups);
	}

	@Override
	public ValidationReport validate(String password) {
		List<String> errors = new ArrayList<>();

		if(password.length() < minLength) {
			errors.add(String.format(ERROR_MIN_LENGTH_TPL, minLength));
		}

		Map<CharacterGroup, MyInteger> occurences = new EnumMap<>(CharacterGroup.class);
		for(char c : password.toCharArray()) {
			if(Character.isLetter(c)) {
				increaseGroup(occurences, CharacterGroup.LETTERS);
				increaseGroup(occurences, Character.isLowerCase(c) ? CharacterGroup.LOWERCASE_LETTERS : CharacterGroup.UPPERCASE_LETTERS);
			} else if(Character.isDigit(c)) {
				increaseGroup(occurences, CharacterGroup.NUMBERS);
			} else {
				increaseGroup(occurences, CharacterGroup.SPECIAL_CHARACTERS);
			}
		}

		int groupsCount = 0;
		for(CharacterGroup group : occurences.keySet()) {
			if(!groups.containsKey(group)) {
				continue;
			}

			groupsCount++;
			if(occurences.get(group).get() < groups.get(group)) {
				errors.add(String.format(ERROR_GROUP_MIN_OCCURENCE_TPL, groups.get(group), group.toReadableString()));
			}
		}

		if(groupsCount < minGroupsCount) {
			String groupsList = groups.keySet().stream().map((group) -> ", " + group.toReadableString()).reduce("", String::concat).trim();
			errors.add(String.format(ERROR_MIN_GROUPS_TPL, minGroupsCount, groupsList.isEmpty() ? groupsList : groupsList.substring(2)));
		}

		return new ValidationReport(errors.isEmpty(), errors);
	}

	private void increaseGroup(Map<CharacterGroup, MyInteger> map, CharacterGroup group) {
		if(!map.containsKey(group)) {
			map.put(group, new MyInteger());
		}

		map.get(group).inc();
	}

	private class MyInteger {
		private int value;

		public void inc() {
			value++;
		}

		public int get() {
			return value;
		}
	}

}
