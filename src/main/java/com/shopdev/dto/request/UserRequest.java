package com.shopdev.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Phải Chứa Ít Nhất 1 Ký Tự")
    @Length(min = 2, message = "Tên Chứa Từ 2 Ký Tự Trở Lên")
    String user_name;

    @Length(min = 8, message = "Mật Khẩu Yêu Cầu 8 Ký Tự Trở Lên")
    String password;

    @Email(message = "Phải là định dạng email")
    String email;
}
