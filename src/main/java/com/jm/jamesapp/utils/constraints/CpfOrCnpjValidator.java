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

        // Remova caracteres não numéricos e verifique o tamanho
        String cleanValue = value.replaceAll("[^0-9]", "");
        if (cleanValue.length() == 11) {
            return validateCpf(cleanValue);
        } else if (cleanValue.length() == 14) {
            return validateCnpj(cleanValue);
        }
        return false; // Valor não é CPF nem CNPJ
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
        if (rest != numbers[10]) {
            return false;
        }

        return true;
    }

    private boolean validateCnpj(String cnpj) {
        // Verifica o tamanho
        if (cnpj.length() != 14) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * ((5 - i) + 1);
        }
        int remainder = sum % 11;
        int firstVerifier = (remainder < 2) ? 0 : (11 - remainder);

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 13; i++) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * ((6 - i) + 1);
        }
        remainder = sum % 11;
        int secondVerifier = (remainder < 2) ? 0 : (11 - remainder);

        // Verifica se os dígitos verificadores calculados correspondem aos dígitos reais
        return (firstVerifier == Character.getNumericValue(cnpj.charAt(12)) &&
                secondVerifier == Character.getNumericValue(cnpj.charAt(13)));

    }
}
