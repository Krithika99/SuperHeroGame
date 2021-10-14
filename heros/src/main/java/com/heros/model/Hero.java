package com.heros.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hero implements Comparable<Hero> {

	@Override
	public int hashCode() {
		return Objects.hash(count, max_power, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hero other = (Hero) obj;
		return max_power == other.max_power && Objects.equals(name, other.name);
	}

	@JsonProperty("name")
	String name;
	@JsonProperty("max_power")
	int max_power;
	int count;

	public Hero(String name) {
		this.name = name;
	}

	public Hero(String name, int max_power, int count) {
		super();
		this.name = name;
		this.max_power = max_power;
		this.count = count;
	}

	@JsonProperty("character")
	String character;

	public Hero() {
		// TODO Auto-generated constructor stub
	}

	public Hero(String name, int max_power) {
		super();
		this.name = name;
		this.max_power = max_power;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMax_power() {
		return max_power;
	}

	public void setMax_power(int max_power) {
		this.max_power = max_power;
	}

	@Override
	public String toString() {
		return "Hero [name=" + name + ", max_power=" + max_power + ", count=" + count + "]";
	}

	@Override
	public int compareTo(Hero o) {

		return this.max_power - o.getMax_power();
	}

}
