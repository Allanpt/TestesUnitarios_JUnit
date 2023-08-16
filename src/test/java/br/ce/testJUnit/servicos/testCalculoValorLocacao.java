package br.ce.testJUnit.servicos;

import br.ce.testJUnit.entidades.Filme;
import br.ce.testJUnit.entidades.Locacao;
import br.ce.testJUnit.entidades.Usuario;
import br.ce.testJUnit.exceptions.FilmeSemEstoqueException;
import br.ce.testJUnit.exceptions.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class testCalculoValorLocacao {

    private LocacaoService service;

    @Parameterized.Parameter
    public List<Filme> filmeList;

    @Parameterized.Parameter(value = 1)
    public Double valorDaLocacao;

    @Parameterized.Parameter(value = 2)
    public String cenario;

    @Before
    public void setup(){
        service = new LocacaoService();
    }

    private static Filme filme1 = new Filme("A procura da felicidade", 1 , 40.0);
    private static Filme filme2 = new Filme("Rei Leao", 3 , 40.0);
    private static Filme filme3 = new Filme("Os Vingadores", 2 , 40.0);
    private static Filme filme4 = new Filme("Homem-Aranha", 4 , 40.0);
    private static Filme filme5 = new Filme("Barbie", 1 , 40.0);
    private static Filme filme6 = new Filme("Um sonho de liberdade", 3 , 40.0);

    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> getParametros(){
        return List.of(new Object[][]{
                {List.of(filme1, filme2, filme3), 110.00, "3 filmes - 25% de desconto no 3°"},
                {List.of(filme1, filme2, filme3,filme4), 130.00, "4 filmes - 50% de desconto no 4°"},
                {List.of(filme1, filme2, filme3,filme4,filme5), 140.00, "5 filmes - 75% de desconto no 5°"},
                {List.of(filme1, filme2, filme3,filme4,filme5,filme6), 140.00, "6 filmes - 100% de desconto no 6°"}
        });

    }

    @Test
    public void deveTerDescontosNoValorDaLocacao() throws FilmeSemEstoqueException, LocadoraException {
        //Cenário
        Usuario user = new Usuario("User 1");

        //Ação
        Locacao resultado = service.alugarFilme(user,filmeList);

        //Verificação
        assertThat(resultado.getValor(), is(valorDaLocacao));
    }
}
