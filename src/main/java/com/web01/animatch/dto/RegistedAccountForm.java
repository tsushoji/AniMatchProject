package com.web01.animatch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登録されたアカウントフォームDtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegistedAccountForm extends BaseAccountForm{}
