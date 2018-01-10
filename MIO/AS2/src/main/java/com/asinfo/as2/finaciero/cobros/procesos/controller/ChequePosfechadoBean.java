/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Cobro;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ChequePosfechadoBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 7422676874905047858L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioCobro servicioCobro;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioDocumento servicioDocumento;
/*  35:    */   private Cobro cobro;
/*  36:    */   private boolean indicadorRender;
/*  37: 62 */   private Date fechaProceso = new Date();
/*  38:    */   private LazyDataModel<Cobro> listaCobro;
/*  39:    */   private DataTable dtCobro;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 78 */     this.listaCobro = new LazyDataModel()
/*  45:    */     {
/*  46:    */       private static final long serialVersionUID = -4089767147932782186L;
/*  47:    */       
/*  48:    */       public List<Cobro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  51:    */         
/*  52: 93 */         filters.put("estado", Estado.ELABORADO.toString());
/*  53: 94 */         filters.put("documento.documentoBase", DocumentoBase.COBRO_CLIENTE.toString());
/*  54: 95 */         filters.put("indicadorTienePosfechados", "true");
/*  55: 96 */         List<Cobro> lista = ChequePosfechadoBean.this.servicioCobro.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  56:    */         
/*  57: 98 */         ChequePosfechadoBean.this.listaCobro.setRowCount(ChequePosfechadoBean.this.servicioCobro.contarPorCriterio(filters));
/*  58:    */         
/*  59:100 */         return lista;
/*  60:    */       }
/*  61:    */     };
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String crear()
/*  65:    */   {
/*  66:118 */     LOG.info("Ingresa en crear");
/*  67:119 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  68:120 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String editar()
/*  72:    */   {
/*  73:129 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  74:130 */     return "";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String guardar()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:140 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86:143 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:145 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:155 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:158 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:160 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:169 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:178 */     crear();
/* 113:179 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String procesarCobroChequePosfechado()
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:194 */       Cobro cobroConDetalle = this.servicioCobro.cargarDetalle(this.cobro.getIdCobro());
/* 121:195 */       cobroConDetalle.setIndicadorTienePosfechados(false);
/* 122:196 */       cobroConDetalle.setEstado(Estado.CONTABILIZADO);
/* 123:197 */       Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(cobroConDetalle.getDocumento().getIdDocumento()));
/* 124:198 */       cobroConDetalle.getDocumento().setTipoAsiento(documento.getTipoAsiento());
/* 125:199 */       cobroConDetalle.setFecha(this.fechaProceso);
/* 126:200 */       this.servicioCobro.guardar(cobroConDetalle);
/* 127:    */       
/* 128:202 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 129:    */     }
/* 130:    */     catch (ExcepcionAS2Financiero e)
/* 131:    */     {
/* 132:204 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 133:    */     }
/* 134:    */     catch (ExcepcionAS2 e)
/* 135:    */     {
/* 136:206 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 137:    */     }
/* 138:    */     catch (Exception e)
/* 139:    */     {
/* 140:208 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo") + e);
/* 141:209 */       e.printStackTrace();
/* 142:    */     }
/* 143:211 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Cobro getCobro()
/* 147:    */   {
/* 148:223 */     return this.cobro;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setCobro(Cobro cobro)
/* 152:    */   {
/* 153:233 */     this.cobro = cobro;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public LazyDataModel<Cobro> getListaCobro()
/* 157:    */   {
/* 158:242 */     return this.listaCobro;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setListaCobro(LazyDataModel<Cobro> listaCobro)
/* 162:    */   {
/* 163:252 */     this.listaCobro = listaCobro;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public DataTable getDtCobro()
/* 167:    */   {
/* 168:261 */     return this.dtCobro;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setDtCobro(DataTable dtCobro)
/* 172:    */   {
/* 173:271 */     this.dtCobro = dtCobro;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isIndicadorRender()
/* 177:    */   {
/* 178:275 */     return this.indicadorRender;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setIndicadorRender(boolean indicadorRender)
/* 182:    */   {
/* 183:279 */     this.indicadorRender = indicadorRender;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Date getFechaProceso()
/* 187:    */   {
/* 188:283 */     return this.fechaProceso;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setFechaProceso(Date fechaProceso)
/* 192:    */   {
/* 193:287 */     this.fechaProceso = fechaProceso;
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.ChequePosfechadoBean
 * JD-Core Version:    0.7.0.1
 */