package lander.expandable;

import java.util.List;

/**
 * Created by magdi on 02/10/2016.
 */
public class ContentItem {
    private String key;
    private String definicao;
    private String informacoesGerais;
    private String legislacao;
    private String requisitosBasicos;
    private String title;
    private List<Procedimento> procedimentosTramites;

    public ContentItem(){
        this.definicao = null;
        this.informacoesGerais = null;
        this.legislacao = null;
        this.requisitosBasicos = null;
        this.title = null;
        this.procedimentosTramites = null;
    }

    public ContentItem(String definicao, String informacoesGerais, String legislacao, String requisitosBasicos, String title, List<Procedimento> procedimentosTramites) {
        this.definicao = definicao;
        this.informacoesGerais = informacoesGerais;
        this.legislacao = legislacao;
        this.requisitosBasicos = requisitosBasicos;
        this.title = title;
        this.procedimentosTramites = procedimentosTramites;
    }

    public String getDefinicao() {
        return definicao;
    }

    public void setDefinicao(String definicao) {
        this.definicao = definicao;
    }

    public String getInformacoesGerais() {
        return informacoesGerais;
    }

    public void setInformacoesGerais(String informacoesGerais) {
        this.informacoesGerais = informacoesGerais;
    }

    public String getLegislacao() {
        return legislacao;
    }

    public void setLegislacao(String legislacao) {
        this.legislacao = legislacao;
    }

    public String getRequisitosBasicos() {
        return requisitosBasicos;
    }

    public void setRequisitosBasicos(String requisitosBasicos) {
        this.requisitosBasicos = requisitosBasicos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Procedimento> getProcedimentosTramites() {
        return procedimentosTramites;
    }

    public void setProcedimentosTramites(List<Procedimento> procedimentosTramites) {
        this.procedimentosTramites = procedimentosTramites;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static class Procedimento{
        private String passo;
        private String procedimentos;
        private String responsavel;

        public Procedimento(){}

        public Procedimento(String passo, String procedimentos, String responsavel) {
            this.passo = passo;
            this.procedimentos = procedimentos;
            this.responsavel = responsavel;
        }

        public String getPasso() {
            return passo;
        }

        public void setPasso(String passo) {
            this.passo = passo;
        }

        public String getProcedimentos() {
            return procedimentos;
        }

        public void setProcedimentos(String procedimentos) {
            this.procedimentos = procedimentos;
        }

        public String getResponsavel() {
            return responsavel;
        }

        public void setResponsavel(String responsavel) {
            this.responsavel = responsavel;
        }
    }


}
