package org.acme.micros.webapp.domain;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Person extends AbstractEntity {

    @NotBlank
    @Size(min = 2, max = 150)
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @PastOrPresent
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    public Person(final String name, final LocalDate birthDate) {
        this.name = normalizeSpace(name);
        this.birthDate = birthDate;
    }

    /**
     * The uniqueness of a {@link Person}'s instance is the same as it is in the database level: {@link Person#name}
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final String otherPersonName = ((Person) other).getName();
        if (isNoneBlank(getName(), otherPersonName)) {
            return getName().equalsIgnoreCase(otherPersonName);
        }

        return false;
    }

    /**
     * @see Person#equals(Object)
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}