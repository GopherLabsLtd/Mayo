package it.gopher.mayo.kotlin.enums

/**
 * Controls how stub responses are returned. There are three main types of stub behaviours:
 * <p>
 * 1. Never - Indicates to never stub the networking response.
 * 2. Immediate - Stub the networking request right away. Good if you want to do immediate assertions
 * 3. delayed - Stub the networking request with a short delay.
 * @author Chamu Rajasekera
 */
public enum class StubBehaviour {
    never,
    immediate,
    delayed,
}