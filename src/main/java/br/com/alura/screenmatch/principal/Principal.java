package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSeries;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private ConverteDados conversor = new ConverteDados();
    private ConsumoApi consumo = new ConsumoApi();
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=68404664";
    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSeries dados = conversor.obterDados(json, DadosSeries.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i=1; i<=dados.totalTemporadas(); i++){
            var json2 = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json2, DadosTemporada.class);
            temporadas.add(dadosTemporada);

        }
        temporadas.forEach(System.out::println);

       /* for(int i = 0; i < dados.totalTemporadas(); i++){
           List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
           for (int j = 0; j < temporadas.size(); j++){
               System.out.println(episodiosTemporada.get(j).numero() + " " + episodiosTemporada.get(j).titulo());
               //System.out.println(episodiosTemporada.get(j).titulo());
           }
           //
        }*/
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.dataLancamento()+" "+e.titulo())));



    }
}
