package br.com.fiap.Checkpoint1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TipoValidator implements ConstraintValidator<Tipo, String>  {
    
    @Override
    public boolean isValid(String tipo, ConstraintValidatorContext arg1) {
        return tipo.equals("CORRENTE") || tipo.equals("POUPANÇA") || tipo.equals("SALÁRIO");
    }
}
