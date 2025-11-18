package student.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import student.entity.Student;

public final class StudentSpecifications {

    private StudentSpecifications() {
    }

    public static Specification<Student> branchEquals(String branch) {
        return (root, query, builder) ->
                StringUtils.hasText(branch)
                        ? builder.equal(builder.lower(root.get("branch")), branch.toLowerCase())
                        : null;
    }

    public static Specification<Student> yopEquals(Integer yop) {
        return (root, query, builder) ->
                yop == null
                        ? null
                        : builder.equal(root.get("yop"), yop);
    }

    public static Specification<Student> isActive(Boolean active) {
        return (root, query, builder) ->
                active == null
                        ? null
                        : builder.equal(root.get("active"), active);
    }
}


