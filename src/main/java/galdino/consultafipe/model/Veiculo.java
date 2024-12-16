package galdino.consultafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(
        @JsonProperty("TipoVeiculo") int tipoVeiculo,
        @JsonProperty("Valor") String valor,
        @JsonProperty("Marca") String marca,
        @JsonProperty("Modelo") String modelo,
        @JsonProperty("AnoModelo") int anoModelo,
        @JsonProperty("Combustivel") String combustivel,
        @JsonProperty("CodigoFipe") String codigoFipe,
        @JsonProperty("MesReferencia") String mesReferencia,
        @JsonProperty("SiglaCombustivel") String siglaCombustivel
) {}
