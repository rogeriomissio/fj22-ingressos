package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.descontos.DescontoDeTrintaPorCentoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoEstudante;
import br.com.caelum.ingresso.model.descontos.SemDesconto;

public class DescontoTest {
 @Test
 public void garanteQueOLugarA1EstaOcupadoEOsLugaresA2EA3Disponiveis(){
     Lugar a1 = new Lugar("A", 1);
     Lugar a2 = new Lugar("A", 2);
     Lugar a3 = new Lugar("A", 3);
     Filme rogueOne = new Filme("Rogue One", Duration.ofMinutes(120),  
                     "SCI_FI", new BigDecimal("12.0"));
     Sala eldorado7 = new Sala("Eldorado 7", new BigDecimal("8.5"));
     Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), rogueOne, eldorado7);
     Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO, a1);
     Set<Ingresso> ingressos = Stream.of(ingresso).collect(Collectors.toSet());
     sessao.setIngressos(ingressos);
     Assert.assertFalse(sessao.isDisponivel(a1));
     Assert.assertTrue(sessao.isDisponivel(a2));
     Assert.assertTrue(sessao.isDisponivel(a3));
 }
 
 @Test
 public void deveConcederDescontoDe30PorcentoParaIngressosDeClientesDeBancos() {
  Lugar lugar = new Lugar("A", 1);
  Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
  Filme filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
  Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
  Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.BANCO, lugar);
  BigDecimal precoEsperado = new BigDecimal("22.75");
  Assert.assertEquals(precoEsperado, ingresso.getPreco());
 }

 @Test
 public void deveConcederDescontoDe50PorcentoParaIngressoDeEstudante() {
  Lugar lugar = new Lugar("A", 1);
  Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
  Filme filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
  Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
  Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.ESTUDANTE, lugar);
  BigDecimal precoEsperado = new BigDecimal("16.25");
  Assert.assertEquals(precoEsperado, ingresso.getPreco());
 }

 @Test
 public void naoDeveConcederDescontoParaIngressoNormal() {
  Lugar lugar = new Lugar("A", 1);
  Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
  Filme filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
  Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
  Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO, lugar);
  BigDecimal precoEsperado = new BigDecimal("32.5");
  Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
}