/*   1:    */ package com.asinfo.as2.finaciero.activos.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.HistoricoDepreciacion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacionMensual;
/*  11:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import javax.faces.model.SelectItem;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class DepreciacionMensualBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1930656486511403844L;
/*  32:    */   @EJB
/*  33:    */   private ServicioDepreciacionMensual servicioDepreciacionMensual;
/*  34:    */   private HistoricoDepreciacion historicoDepreciacion;
/*  35:    */   private boolean indicadorEdicionTipoAsientoNIIF;
/*  36:    */   private boolean indicadorEdicionTipoAsientoFiscal;
/*  37:    */   private LazyDataModel<HistoricoDepreciacion> listaHistoricoDepreciacion;
/*  38:    */   private List<SelectItem> listaMesCombo;
/*  39:    */   private DataTable dtDepreciacionMensual;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 81 */     this.listaHistoricoDepreciacion = new LazyDataModel()
/*  45:    */     {
/*  46:    */       private static final long serialVersionUID = 1L;
/*  47:    */       
/*  48:    */       public List<HistoricoDepreciacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 88 */         List<HistoricoDepreciacion> lista = new ArrayList();
/*  51: 89 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  52:    */         
/*  53: 91 */         lista = DepreciacionMensualBean.this.servicioDepreciacionMensual.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  54:    */         
/*  55: 93 */         DepreciacionMensualBean.this.listaHistoricoDepreciacion.setRowCount(DepreciacionMensualBean.this.servicioDepreciacionMensual.contarPorCriterio(filters));
/*  56:    */         
/*  57: 95 */         return lista;
/*  58:    */       }
/*  59:    */     };
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void crearHistoricoDepreciacion()
/*  63:    */   {
/*  64:109 */     this.historicoDepreciacion = new HistoricoDepreciacion();
/*  65:110 */     this.historicoDepreciacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  66:111 */     this.historicoDepreciacion.setSucursal(AppUtil.getSucursal());
/*  67:112 */     this.historicoDepreciacion.setEstado(Estado.ELABORADO);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String editar()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:124 */       if (getHistoricoDepreciacion().getIdHistoricoDepreciacion() > 0)
/*  75:    */       {
/*  76:125 */         this.servicioDepreciacionMensual.esEditable(this.historicoDepreciacion);
/*  77:126 */         setEditado(true);
/*  78:    */       }
/*  79:    */       else
/*  80:    */       {
/*  81:128 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  82:    */       }
/*  83:    */     }
/*  84:    */     catch (ExcepcionAS2Financiero e)
/*  85:    */     {
/*  86:131 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  87:    */     }
/*  88:134 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String guardar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  96:145 */       setEditado(false);
/*  97:146 */       limpiar();
/*  98:    */     }
/*  99:    */     catch (Exception e)
/* 100:    */     {
/* 101:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 102:149 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 103:    */     }
/* 104:151 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String eliminar()
/* 108:    */   {
/* 109:162 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 110:163 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String cargarDatos()
/* 114:    */   {
/* 115:172 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String limpiar()
/* 119:    */   {
/* 120:181 */     crearHistoricoDepreciacion();
/* 121:182 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String depreciar()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:187 */       this.servicioDepreciacionMensual.depreciar(this.historicoDepreciacion);
/* 129:188 */       setEditado(false);
/* 130:189 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 131:    */     }
/* 132:    */     catch (ExcepcionAS2Financiero e)
/* 133:    */     {
/* 134:191 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 135:192 */       LOG.error(e);
/* 136:    */     }
/* 137:    */     catch (ExcepcionAS2 e)
/* 138:    */     {
/* 139:194 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 140:195 */       LOG.error(e);
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:197 */       e.printStackTrace();
/* 145:    */     }
/* 146:199 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public HistoricoDepreciacion getHistoricoDepreciacion()
/* 150:    */   {
/* 151:216 */     if (this.historicoDepreciacion == null) {
/* 152:217 */       crearHistoricoDepreciacion();
/* 153:    */     }
/* 154:219 */     return this.historicoDepreciacion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setHistoricoDepreciacion(HistoricoDepreciacion historicoDepreciacion)
/* 158:    */   {
/* 159:229 */     this.historicoDepreciacion = historicoDepreciacion;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<SelectItem> getListaMesCombo()
/* 163:    */   {
/* 164:238 */     if (this.listaMesCombo == null)
/* 165:    */     {
/* 166:239 */       this.listaMesCombo = new ArrayList();
/* 167:240 */       for (Mes mes : Mes.values())
/* 168:    */       {
/* 169:241 */         SelectItem itemMes = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 170:242 */         this.listaMesCombo.add(itemMes);
/* 171:    */       }
/* 172:    */     }
/* 173:245 */     return this.listaMesCombo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setListaMesCombo(List<SelectItem> listaMesCombo)
/* 177:    */   {
/* 178:255 */     this.listaMesCombo = listaMesCombo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public LazyDataModel<HistoricoDepreciacion> getListaHistoricoDepreciacion()
/* 182:    */   {
/* 183:264 */     return this.listaHistoricoDepreciacion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setListaHistoricoDepreciacion(LazyDataModel<HistoricoDepreciacion> listaHistoricoDepreciacion)
/* 187:    */   {
/* 188:274 */     this.listaHistoricoDepreciacion = listaHistoricoDepreciacion;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public DataTable getDtDepreciacionMensual()
/* 192:    */   {
/* 193:283 */     return this.dtDepreciacionMensual;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setDtDepreciacionMensual(DataTable dtDepreciacionMensual)
/* 197:    */   {
/* 198:293 */     this.dtDepreciacionMensual = dtDepreciacionMensual;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean isIndicadorEdicionTipoAsientoNIIF()
/* 202:    */   {
/* 203:302 */     return this.indicadorEdicionTipoAsientoNIIF;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setIndicadorEdicionTipoAsientoNIIF(boolean indicadorEdicionTipoAsientoNIIF)
/* 207:    */   {
/* 208:312 */     this.indicadorEdicionTipoAsientoNIIF = indicadorEdicionTipoAsientoNIIF;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean isIndicadorEdicionTipoAsientoFiscal()
/* 212:    */   {
/* 213:321 */     return this.indicadorEdicionTipoAsientoFiscal;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setIndicadorEdicionTipoAsientoFiscal(boolean indicadorEdicionTipoAsientoFiscal)
/* 217:    */   {
/* 218:331 */     this.indicadorEdicionTipoAsientoFiscal = indicadorEdicionTipoAsientoFiscal;
/* 219:    */   }
/* 220:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.procesos.DepreciacionMensualBean
 * JD-Core Version:    0.7.0.1
 */