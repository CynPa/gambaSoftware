/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*   9:    */ import com.asinfo.as2.utils.JsfUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.primefaces.component.datatable.DataTable;
/*  18:    */ import org.primefaces.model.LazyDataModel;
/*  19:    */ import org.primefaces.model.SortOrder;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class ConsultaCobroBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1425588770056975545L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioCobro servicioCobro;
/*  29:    */   private boolean indicadorCobrosSinCierreCaja;
/*  30:    */   private boolean indicadorCierreCajaSinDeposito;
/*  31: 53 */   private Integer opcion = Integer.valueOf(0);
/*  32:    */   private DetalleFormaCobro detalleFormaCobro;
/*  33:    */   private LazyDataModel<DetalleFormaCobro> listaDetalleFormaCobro;
/*  34:    */   private DataTable dtConsultaCobro;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 65 */     this.listaDetalleFormaCobro = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<DetalleFormaCobro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 72 */         List<DetalleFormaCobro> lista = new ArrayList();
/*  46:    */         
/*  47: 74 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  48: 75 */         filters.put("cobro.estado", "!=" + Estado.ANULADO);
/*  49: 79 */         if (ConsultaCobroBean.this.opcion.intValue() == 1) {
/*  50: 80 */           filters.put("detalleCierreCaja", "null");
/*  51:    */         }
/*  52: 82 */         if (ConsultaCobroBean.this.opcion.intValue() == 2) {
/*  53: 83 */           filters.put("detalleCierreCaja.interfazContableProceso", "null");
/*  54:    */         }
/*  55: 86 */         lista = ConsultaCobroBean.this.servicioCobro.obtenerListaPorPaginaConsulta(startIndex, pageSize, sortField, ordenar, filters);
/*  56: 87 */         ConsultaCobroBean.this.listaDetalleFormaCobro.setRowCount(ConsultaCobroBean.this.servicioCobro.contarPorCriterioConsulta(filters));
/*  57:    */         
/*  58: 89 */         return lista;
/*  59:    */       }
/*  60:    */     };
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void procesar() {}
/*  64:    */   
/*  65:    */   public void cargarDatosCobro()
/*  66:    */   {
/*  67:100 */     this.detalleFormaCobro = ((DetalleFormaCobro)this.dtConsultaCobro.getRowData());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void liberarCobro()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:106 */       this.servicioCobro.liberarCobro(this.detalleFormaCobro);
/*  75:107 */       addInfoMessage(getLanguageController().getMensaje("msg_info_liberacion_cobro"));
/*  76:    */     }
/*  77:    */     catch (AS2Exception e)
/*  78:    */     {
/*  79:109 */       e.printStackTrace();
/*  80:110 */       JsfUtil.addErrorMessage(e, "");
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String editar()
/*  85:    */   {
/*  86:121 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  87:122 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String guardar()
/*  91:    */   {
/*  92:133 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  93:134 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String eliminar()
/*  97:    */   {
/*  98:144 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  99:145 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String limpiar()
/* 103:    */   {
/* 104:155 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String cargarDatos()
/* 108:    */   {
/* 109:165 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public DetalleFormaCobro getDetalleFormaCobro()
/* 113:    */   {
/* 114:174 */     return this.detalleFormaCobro;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDetalleFormaCobro(DetalleFormaCobro detalleFormaCobro)
/* 118:    */   {
/* 119:184 */     this.detalleFormaCobro = detalleFormaCobro;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public DataTable getDtConsultaCobro()
/* 123:    */   {
/* 124:193 */     return this.dtConsultaCobro;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDtConsultaCobro(DataTable dtConsultaCobro)
/* 128:    */   {
/* 129:203 */     this.dtConsultaCobro = dtConsultaCobro;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public LazyDataModel<DetalleFormaCobro> getListaDetalleFormaCobro()
/* 133:    */   {
/* 134:212 */     return this.listaDetalleFormaCobro;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setListaDetalleFormaCobro(LazyDataModel<DetalleFormaCobro> listaDetalleFormaCobro)
/* 138:    */   {
/* 139:222 */     this.listaDetalleFormaCobro = listaDetalleFormaCobro;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isIndicadorCobrosSinCierreCaja()
/* 143:    */   {
/* 144:231 */     return this.indicadorCobrosSinCierreCaja;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setIndicadorCobrosSinCierreCaja(boolean indicadorCobrosSinCierreCaja)
/* 148:    */   {
/* 149:241 */     this.indicadorCobrosSinCierreCaja = indicadorCobrosSinCierreCaja;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isIndicadorCierreCajaSinDeposito()
/* 153:    */   {
/* 154:250 */     return this.indicadorCierreCajaSinDeposito;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setIndicadorCierreCajaSinDeposito(boolean indicadorCierreCajaSinDeposito)
/* 158:    */   {
/* 159:260 */     this.indicadorCierreCajaSinDeposito = indicadorCierreCajaSinDeposito;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Integer getOpcion()
/* 163:    */   {
/* 164:269 */     return this.opcion;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setOpcion(Integer opcion)
/* 168:    */   {
/* 169:279 */     this.opcion = opcion;
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ConsultaCobroBean
 * JD-Core Version:    0.7.0.1
 */