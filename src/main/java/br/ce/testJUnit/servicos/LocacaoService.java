package br.ce.testJUnit.servicos;

import static br.ce.testJUnit.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.testJUnit.entidades.Filme;
import br.ce.testJUnit.entidades.Locacao;
import br.ce.testJUnit.entidades.Usuario;
import br.ce.testJUnit.exceptions.FilmeSemEstoqueException;
import br.ce.testJUnit.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmeList) throws FilmeSemEstoqueException, LocadoraException {

		if (usuario == null){
			throw new LocadoraException("Usuário vazio");
		}
		if (filmeList.isEmpty()){
			throw new LocadoraException("Filme vazio");
		}
		for (Filme filme: filmeList){
			if (filme.getEstoque() == 0 ){
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}

		Locacao locacao = new Locacao();
		locacao.setFilmeList(filmeList);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0.0;

		for (int i = 0; i < filmeList.size(); i++) {

			Filme filme = filmeList.get(i);

			switch (i){
				case 2: valorTotal += filme.getPrecoLocacao() * 0.75; break;
				case 3: valorTotal += filme.getPrecoLocacao() * 0.5; break;
				case 4: valorTotal += filme.getPrecoLocacao() * 0.25; break;
				case 5: valorTotal += filme.getPrecoLocacao() * 0.0; break;
				default: valorTotal += filme.getPrecoLocacao(); break;
			}
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}


}