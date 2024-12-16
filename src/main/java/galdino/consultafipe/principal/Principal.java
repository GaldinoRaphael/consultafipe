package galdino.consultafipe.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import galdino.consultafipe.model.BasicResponse;
import galdino.consultafipe.model.Marcas;
import galdino.consultafipe.model.Modelos;
import galdino.consultafipe.model.Veiculo;
import galdino.consultafipe.service.ConsumoApi;
import galdino.consultafipe.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private Scanner entrada = new Scanner(System.in);
    private ObjectMapper mapper = new ObjectMapper();

    public void exibeMenu(){
        var endereco = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
        var json = consumo.obterDados(endereco);

        List<Marcas> marcas = conversor.obterDadosComoLista(json, new TypeReference<List<Marcas>>() {});

        marcas.stream().sorted(Comparator.comparing(Marcas::nome)).forEach(e-> System.out.println("Cód: " + e.codigo() + " descrição: " + e.nome()));

        System.out.println("Insira o código da marca: ");
        var marcaCodigo = entrada.nextLine();

        var modelosJson = consumo.obterDados(endereco + "/" + marcaCodigo + "/modelos");

        Modelos modelos = conversor.obterDados(modelosJson, Modelos.class);

        modelos.modelos().forEach(e-> System.out.println("Cód: " + e.codigo() + " descrição: " + e.nome()));

        System.out.println("Digite o trecho do nome do veiculo: ");
        var nome = entrada.nextLine();

        modelos.modelos().stream().filter(e -> e.nome().toLowerCase().contains(nome.toLowerCase())).forEach(e-> System.out.println("Cód: " + e.codigo() + " descrição: " + e.nome()));

        System.out.println("Digite o código do modelo para consultar os valores: ");
        var codigoModelo = entrada.nextLine();

        var anosModeloJson = consumo.obterDados(endereco + "/" + marcaCodigo + "/modelos/" + codigoModelo + "/anos");

        var anosModelo = conversor.obterDadosComoLista(anosModeloJson, new TypeReference<List<BasicResponse>>() {});

        var veiculos = anosModelo.stream().map(e -> {
            var veiculoJson = consumo.obterDados(endereco + "/" + marcaCodigo + "/modelos/" + codigoModelo + "/anos/" + e.codigo());
            return conversor.obterDados(veiculoJson, Veiculo.class);
        }).toList();

        veiculos.stream().sorted(Comparator.comparing(Veiculo::valor)).forEach(System.out::println);

        //System.out.println(veiculos);

    }
}
