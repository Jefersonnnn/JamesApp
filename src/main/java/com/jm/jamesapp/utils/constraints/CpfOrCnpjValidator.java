package com.jm.jamesapp.utils.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfOrCnpjValidator implements ConstraintValidator<ValidCpfOrCnpj, String> {
    @Override
    public void initialize(ValidCpfOrCnpj constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }

        String cleanValue = cleanStringValue(value);
        if (cleanValue.length() == 11) {
            return validateCpf(cleanValue);
        } else if (cleanValue.length() == 14) {
            return validateCnpj(cleanValue);
        }
        return false;
    }

    private boolean validateCpf(String cpf) {
        if (!cpf.matches("\\d{11}") || cpf.equals("00000000000")) {
            return false;
        }

        // Verifique a validade do CPF
        int[] numbers = new int[11];
        for (int i = 0; i < 11; i++) {
            numbers[i] = Integer.parseInt(cpf.substring(i, i + 1));
        }

        int sum = 0;
        int rest;
        for (int i = 1; i <= 9; i++) {
            sum = sum + numbers[i - 1] * (11 - i);
        }
        rest = (sum * 10) % 11;

        if ((rest == 10) || (rest == 11)) {
            rest = 0;
        }
        if (rest != numbers[9]) {
            return false;
        }

        sum = 0;
        for (int i = 1; i <= 10; i++) {
            sum = sum + numbers[i - 1] * (12 - i);
        }
        rest = (sum * 10) % 11;

        if ((rest == 10) || (rest == 11)) {
            rest = 0;
        }
        return rest == numbers[10];
    }

    private boolean validateCnpj(String cnpj) {
        if (cnpj == null) return false;
        if (cnpj.startsWith("00000000000000")) return false;
        if (cnpj.substring(0, 1).isEmpty()) return false;

        int iSoma = 0, iDigito;
        char[] chCaracteresCNPJ;
        String strCNPJ_Calculado;

        try {
            cnpj = cnpj.replace('.', ' ');
            cnpj = cnpj.replace('/', ' ');
            cnpj = cnpj.replace('-', ' ');
            cnpj = cnpj.replaceAll(" ", "");
            strCNPJ_Calculado = cnpj.substring(0, 12);
            if (cnpj.length() != 14) return false;
            chCaracteresCNPJ = cnpj.toCharArray();
            for (int i = 0; i < 4; i++) {
                if ((chCaracteresCNPJ[i] - 48 >= 0) && (chCaracteresCNPJ[i] - 48 <= 9)) {
                    iSoma += (chCaracteresCNPJ[i] - 48) * (6 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if ((chCaracteresCNPJ[i + 4] - 48 >= 0) && (chCaracteresCNPJ[i + 4] - 48 <= 9)) {
                    iSoma += (chCaracteresCNPJ[i + 4] - 48) * (10 - (i + 1));
                }
            }
            iDigito = 11 - (iSoma % 11);
            strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);

            iSoma = 0;
            for (int i = 0; i < 5; i++) {
                if ((chCaracteresCNPJ[i] - 48 >= 0) && (chCaracteresCNPJ[i] - 48 <= 9)) {
                    iSoma += (chCaracteresCNPJ[i] - 48) * (7 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if ((chCaracteresCNPJ[i + 5] - 48 >= 0) && (chCaracteresCNPJ[i + 5] - 48 <= 9)) {
                    iSoma += (chCaracteresCNPJ[i + 5] - 48) * (10 - (i + 1));
                }
            }
            iDigito = 11 - (iSoma % 11);
            strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
            return cnpj.equals(strCNPJ_Calculado);
        } catch (Exception e) {
            return false;
        }
    }

    // Remova caracteres não numéricos e verifique o tamanho
    public static String cleanStringValue(String value) {
        return value.replaceAll("[^0-9]", "");
    }
}
