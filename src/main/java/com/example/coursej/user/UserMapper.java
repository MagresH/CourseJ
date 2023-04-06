package com.example.coursej.user;

import com.example.coursej.course.Course;
import com.example.coursej.course.CourseService;
import com.example.coursej.enrollment.Enrollment;
import com.example.coursej.enrollment.EnrollmentService;
import com.example.coursej.mapper.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper extends BaseMapper<User, UserDTO>{

        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

        @Mappings({
                @Mapping(source = "enrollments", target = "enrollmentsIds", qualifiedByName = "toEnrollmentsIdList"),
                @Mapping(source = "courses", target = "coursesIds", qualifiedByName = "toCoursesIdList")
        })
        UserDTO toDTO(User user );

        @Mappings({
                @Mapping(source = "enrollmentsIds", target = "enrollments",qualifiedByName = "toEnrollmentsList"),
                @Mapping(source = "coursesIds", target = "courses", qualifiedByName = "toCoursesList")
        })
        User toEntity(UserDTO userDTO);

        @IterableMapping(qualifiedByName = "toDTO")
        default List<UserDTO> toDTOList(List<User> userList) {
            return userList.stream()
                    .map(this::toDTO)
                    .toList();
        }

        @IterableMapping(qualifiedByName = "toEntity")
        default List<User> toEntityList(List<UserDTO> userDTOList) {
            return userDTOList.stream()
                    .map(this::toEntity)
                    .toList();
        }
        @Named("toEnrollmentsIdList")
        default List<Long> toEnrollmentsIdList(List<Enrollment> enrollments) {
                return enrollments.stream()
                        .map(Enrollment::getId)
                        .toList();
        }
        @Named("toCoursesIdList")
        default List<Long> toCoursesIdList(List<Course> courses) {
                return courses.stream()
                        .map(Course::getId)
                        .toList();
        }
//        @Named("toEnrollmentsList")
//        default List<Enrollment> toEnrollmentsList(List<Long> enrollmentsIds,
//                                                   @Context EnrollmentService enrollmentService) {
//                return enrollmentsIds.stream()
//                        .map(enrollmentService::getEnrollmentById)
//                        .toList();
//        }
//        @Named("toCoursesList")
//        default List<Course> toCoursesList(List<Long> coursesIds,
//                                           @Context CourseService courseService) {
//                return coursesIds.stream()
//                        .map(courseService::getCourseById)
//                        .toList();
//        }

    }


