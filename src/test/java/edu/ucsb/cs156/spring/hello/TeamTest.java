package edu.ucsb.cs156.spring.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class TeamTest {

    Team team;

    @BeforeEach
    public void setup() {
        team = new Team("test-team");    
    }

    @Test
    public void getName_returns_correct_name() {
       assert(team.getName().equals("test-team"));
    }

   
    // TODO: Add additional tests as needed to get to 100% jacoco line coverage, and
    // 100% mutation coverage (all mutants timed out or killed)
    
    //test for addmember function 
    @Test
    public void addMember_adds_member_correctly() {
        team.addMember("Rishik B.");
        assertEquals(1, team.getMembers().size());
        assertEquals("Rishik B.", team.getMembers().get(0));
    }

    //test whether setting team naem works
    @Test
    public void setName_sets_name_correctly() {
        team.setName("s25-08");
        assertEquals("s25-08", team.getName());
    }

    //check whether setting member works as expected
    @Test
    public void setMembers_sets_members_correctly() {
        ArrayList<String> newMembers = new ArrayList<>();
        newMembers.add("Hannah");
        team.setMembers(newMembers);
        assertEquals(newMembers, team.getMembers());
    }

    //check team.to_string function
    @Test
    public void toString_contains_team_name_and_members() {
        team.addMember("Giovanni");
        String result = team.toString();
        assert(result.contains("test-team"));
        assert(result.contains("Giovanni"));
    }

    //check for consistent hash
    @Test
    public void hashCode_returns_consistent_hash() {
        team.addMember("Nathan");
        int hash1 = team.hashCode();
        int hash2 = team.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    public void hashCode_is_consistent_and_matches_manual_hash() {
        team.setName("test-team");
        team.setMembers(new ArrayList<>());
        int expectedHash = "test-team".hashCode() | new ArrayList<>().hashCode();
        assertEquals(expectedHash, team.hashCode());
    }

    //check whether teams are equal
    @Test
    public void equals_returns_true_for_equal_teams() {
        Team other = new Team("test-team");
        other.setMembers(new ArrayList<>());
        assertEquals(team, other);
    }

    @Test
    public void equals_returns_false_for_different_teams() {
        Team other = new Team("other-team");
        assert(!team.equals(other));
    }

    @Test
    public void equals_returns_false_for_non_team_object() {
        assert(!team.equals("not a team"));
    }
    
    @Test
    public void equals_returns_true_for_same_object_reference() {
        assertEquals(true, team.equals(team));
    }

    @Test
    public void equals_returns_true_for_object_with_same_name_and_members() {
        Team other = new Team("test-team");
        other.setMembers(new ArrayList<>());
        assertEquals(team, other);
    }

    @Test
    public void equals_returns_false_when_names_match_but_members_differ() {
        Team other = new Team("test-team");
        ArrayList<String> members = new ArrayList<>();
        members.add("Different Member");
        other.setMembers(members);
        assert(!team.equals(other));
    }

    @Test
    public void equals_returns_false_when_members_match_but_names_differ() {
        Team other = new Team("different-name");
        other.setMembers(team.getMembers());
        assert(!team.equals(other));
    }
}
