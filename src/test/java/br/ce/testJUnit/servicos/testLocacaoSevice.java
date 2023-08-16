package br.ce.testJUnit.servicos;

import br.ce.testJUnit.entidades.Filme;
import br.ce.testJUnit.entidades.Locacao;
import br.ce.testJUnit.entidades.Usuario;
import br.ce.testJUnit.exceptions.FilmeSemEstoqueException;
import br.ce.testJUnit.exceptions.LocadoraException;
import br.ce.testJUnit.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static br.ce.testJUnit.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class testLocacaoSevice {

    private LocacaoService service;
    private List<Filme> filmeList;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup(){
        filmeList = new ArrayList<>();
        service = new LocacaoService();
    }

    @Test
    public void teste1() throws Exception {

        //Cenário
        Usuario user = new Usuario("allan");
        List<Filme> filmeList = List.of(new Filme("A procura da felicidade", 1, 40.00));

        //Ação
        Locacao locacao = service.alugarFilme(user,filmeList);

        //Verificação
        error.checkThat(locacao.getValor(), is(equalTo((40.0))) );
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
    }

    @Test
    public void naoDeveTerFilmeEmEstoque() throws LocadoraException {

        //Cenário
        Usuario user = new Usuario("allan");
        List<Filme> filmeList = List.of(new Filme("A procura da felicidade", 0, 40.00));

        //Ação
        try {
            service.alugarFilme(user,filmeList);
            Assert.fail();
        } catch (FilmeSemEstoqueException e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }

    }
    @Test
    public void deveTerUsuárioVazio() throws FilmeSemEstoqueException {

        //Cenário
        List<Filme> filmeList = List.of(new Filme("A procura da felicidade", 1, 40.00));

        //Ação
        try {
            service.alugarFilme(null,filmeList);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }

    }
    @Test
    public void deveTerFilmeVazio() throws FilmeSemEstoqueException {

        //Cenário
        Usuario user = new Usuario("allan");

        //Ação
        try {
            service.alugarFilme(user,filmeList);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Filme vazio"));
        }

    }

    @Test
    public void deveTerDescontoDe25NoFilme3() throws FilmeSemEstoqueException, LocadoraException {
        //Cenário
        Usuario user = new Usuario("User 1");
        List<Filme> filmeList2 = List.of(new Filme("A procura da felicidade", 1, 40.00), new Filme("Avatar", 2, 40.0),
                new Filme("Homem-Aranha", 3, 40.0));

        //Ação
        Locacao resultado = service.alugarFilme(user,filmeList2);

        //Verificação
        assertThat(resultado.getValor(), is(110.0));
    }

    @Test
    public void deveTerDescontoDe50NoFilme4() throws FilmeSemEstoqueException, LocadoraException {
        //Cenário
        Usuario user = new Usuario("User 2");
        List<Filme> filmeList3 = List.of(new Filme("A procura da felicidade", 1, 40.00), new Filme("Avatar", 2, 40.0),
                new Filme("Homem-Aranha", 3, 40.0), new Filme("Harry Potter", 2, 40.0));

        //Ação
        Locacao resultado = service.alugarFilme(user,filmeList3);

        //Verificação
        assertThat(resultado.getValor(), is(130.0));
    }
    @Test
    public void deveTerDescontoDe75NoFilme5() throws FilmeSemEstoqueException, LocadoraException {
        //Cenário
        Usuario user = new Usuario("User 2");
        List<Filme> filmeList4 = List.of(new Filme("A procura da felicidade", 1, 40.00), new Filme("Avatar", 2, 40.0),
                new Filme("Homem-Aranha", 3, 40.0), new Filme("Harry Potter", 2, 40.0),
                new Filme("Barbie", 1 , 40.0));

        //Ação
        Locacao resultado = service.alugarFilme(user,filmeList4);

        //Verificação
        assertThat(resultado.getValor(), is(140.0));
    }
    @Test
    public void deveTerDescontoDe100NoFilme6() throws FilmeSemEstoqueException, LocadoraException {
        //Cenário
        Usuario user = new Usuario("User 2");
        List<Filme> filmeList4 = List.of(new Filme("A procura da felicidade", 1, 40.00), new Filme("Avatar", 2, 40.0),
                new Filme("Homem-Aranha", 3, 40.0), new Filme("Harry Potter", 2, 40.0),
                new Filme("Barbie", 1 , 40.0), new Filme("Rei Leão", 5, 40.0));

        //Ação
        Locacao resultado = service.alugarFilme(user,filmeList4);

        //Verificação
        assertThat(resultado.getValor(), is(140.0));
    }
}
