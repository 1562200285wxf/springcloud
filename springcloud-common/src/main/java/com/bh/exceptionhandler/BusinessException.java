package com.bh.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/23 22:09
 * @Description: TODO
 */
@Data
@AllArgsConstructor  //�����в������췽��
@NoArgsConstructor   //�����޲�������
public class BusinessException extends RuntimeException {
    private Integer code;//״̬��
    private String msg;//�쳣��Ϣ
}
