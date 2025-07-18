package com.shopdev.dto.request;


import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {
    @Length(min = 2, message = "Tên Chứa Từ 2 Ký Tự Trở Lên")
    String fullName;

    @Length(min = 8, message = "Mật Khẩu Yêu Cầu 8 Ký Tự Trở Lên")
    String password;

    @Email(message = "Phải là định dạng email")
    String email;
}
