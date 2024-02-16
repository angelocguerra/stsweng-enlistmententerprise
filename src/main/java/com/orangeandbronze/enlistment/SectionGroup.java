package com.orangeandbronze.enlistment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

class SectionGroup {
    private final Collection<Section> sections;

    SectionGroup() {
        this.sections = new HashSet<>();
    }

    void addSection(Section newSection) {
        requireNonNull(newSection, "Section cannot be null");

        this.sections.add(newSection);
    }

    Collection<Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionGroup that = (SectionGroup) o;
        return Objects.equals(sections, that.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sections);
    }

    @Override
    public String toString() {
        return  "All Sections: " + sections;
    }
}
