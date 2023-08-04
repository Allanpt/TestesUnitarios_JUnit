package br.ce.testJUnit.servicos;

import br.ce.testJUnit.entidades.Filme;
import br.ce.testJUnit.entidades.Locacao;
import br.ce.testJUnit.entidades.Usuario;
import br.ce.testJUnit.utils.DataUtils;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class testLocacaoSevice {

    @Test
    public void teste1() {

        //Cenário
        LocacaoService service = new LocacaoService();
        Usuario user = new Usuario("allan");
        Filme movie = new Filme("A procura da felicidade", 2 , 40.00);

        //Ação
        Locacao locacao = service.alugarFilme(user,movie);

        //Verificação
        assertThat(locacao.getValor(), is(equalTo((40.0))) );
        assertThat(locacao.getValor(), not(50));
        assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
    }
}
