package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class DescontoEstudante implements Desconto {
	private BigDecimal metade = new BigDecimal("2.00");

	@Override
	public String getDescricao() {
		return "Desconto Estudante";
	}

	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal) {
		return precoOriginal.divide(metade);
	}
}
