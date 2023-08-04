package br.ce.testJUnit.servicos;

import br.ce.testJUnit.entidades.Filme;
import br.ce.testJUnit.entidades.Locacao;
import br.ce.testJUnit.entidades.Usuario;
import br.ce.testJUnit.exceptions.FilmeSemEstoqueException;
import br.ce.testJUnit.exceptions.LocadoraException;
import br.ce.testJUnit.utils.DataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Date;

import static br.ce.testJUnit.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class testLocacaoSevice {

    private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup(){
        service = new LocacaoService();
    }


    @Test
    public void teste1() throws Exception {

        //Cenário
        Usuario user = new Usuario("allan");
        Filme movie = new Filme("A procura da felicidade", 1 , 40.00);

        //Ação
        Locacao locacao = service.alugarFilme(user,movie);

        //Verificação
        error.checkThat(locacao.getValor(), is(equalTo((40.0))) );
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
    }

    @Test
    public void FilmeSemEstoque() throws LocadoraException {

        //Cenário
        Usuario user = new Usuario("allan");
        Filme movie = new Filme("A procura da felicidade", 0 , 40.00);

        //Ação
        try {
            service.alugarFilme(user,movie);
            Assert.fail();
        } catch (FilmeSemEstoqueException e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }

    }
    @Test
    public void UsuárioVazio() throws FilmeSemEstoqueException {

        //Cenário
        Filme movie = new Filme("A procura da felicidade", 1 , 40.00);

        //Ação
        try {
            service.alugarFilme(null,movie);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }

    }
    @Test
    public void FilmeVazio() throws FilmeSemEstoqueException {

        //Cenário
        Usuario user = new Usuario("allan");

        //Ação
        try {
            service.alugarFilme(user,null);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Filme vazio"));
        }

    }
}
