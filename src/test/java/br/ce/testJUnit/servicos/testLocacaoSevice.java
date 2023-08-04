package br.ce.testJUnit.servicos;

import br.ce.testJUnit.entidades.Filme;
import br.ce.testJUnit.entidades.Locacao;
import br.ce.testJUnit.entidades.Usuario;
import br.ce.testJUnit.utils.DataUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

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
        Assert.assertEquals(40, locacao.getValor(), 0.0);
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
    }
}
