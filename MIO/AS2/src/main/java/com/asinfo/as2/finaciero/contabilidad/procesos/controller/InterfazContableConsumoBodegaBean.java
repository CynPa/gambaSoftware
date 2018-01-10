/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Calendar;
/*  19:    */ import java.util.Collection;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.faces.context.FacesContext;
/*  27:    */ import javax.faces.context.PartialViewContext;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.component.datatable.DataTable;
/*  30:    */ import org.primefaces.context.RequestContext;
/*  31:    */ import org.primefaces.model.LazyDataModel;
/*  32:    */ import org.primefaces.model.SortOrder;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class InterfazContableConsumoBodegaBean
/*  37:    */   extends PageControllerAS2
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -8914881290932226380L;
/*  40:    */   @EJB
/*  41:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  42:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  43:    */   private DataTable dtDetalleAsiento;
/*  44:    */   private BigDecimal debe;
/*  45:    */   private BigDecimal haber;
/*  46:    */   private InterfazContableProceso interfazContableProceso;
/*  47:    */   private DataTable dtInterfazContableProceso;
/*  48: 69 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  49:    */   
/*  50:    */   @PostConstruct
/*  51:    */   public void init()
/*  52:    */   {
/*  53: 74 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  54:    */     {
/*  55:    */       private static final long serialVersionUID = 763093382591716471L;
/*  56:    */       
/*  57:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  58:    */       {
/*  59: 81 */         List<InterfazContableProceso> lista = new ArrayList();
/*  60:    */         
/*  61: 83 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  62: 84 */         filters.put("documentoBase", String.valueOf(DocumentoBase.INTERFAZ_CONSUMOS_BODEGA));
/*  63: 85 */         lista = InterfazContableConsumoBodegaBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  64:    */         
/*  65: 87 */         InterfazContableConsumoBodegaBean.this.listaInterfazContableProceso.setRowCount(InterfazContableConsumoBodegaBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  66: 88 */         return lista;
/*  67:    */       }
/*  68:    */     };
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String crear()
/*  72:    */   {
/*  73:100 */     limpiar();
/*  74:101 */     setEditado(true);
/*  75:    */     
/*  76:103 */     return "";
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String editar()
/*  80:    */   {
/*  81:113 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  82:114 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String guardar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:127 */       this.servicioInterfazContableProceso.guardar(this.interfazContableProceso);
/*  90:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  91:    */       
/*  92:130 */       cargarDatos();
/*  93:    */     }
/*  94:    */     catch (ExcepcionAS2Financiero e)
/*  95:    */     {
/*  96:132 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  97:133 */       e.printStackTrace();
/*  98:    */     }
/*  99:    */     catch (ExcepcionAS2 e)
/* 100:    */     {
/* 101:136 */       e.printStackTrace();
/* 102:    */     }
/* 103:139 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String eliminar()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:152 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 111:    */       
/* 112:154 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 113:    */       
/* 114:156 */       cargarDatos();
/* 115:    */     }
/* 116:    */     catch (ExcepcionAS2Financiero e)
/* 117:    */     {
/* 118:159 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 119:160 */       e.printStackTrace();
/* 120:    */     }
/* 121:    */     catch (ExcepcionAS2 e)
/* 122:    */     {
/* 123:162 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 124:163 */       e.printStackTrace();
/* 125:    */     }
/* 126:166 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String limpiar()
/* 130:    */   {
/* 131:176 */     setEditado(false);
/* 132:177 */     this.interfazContableProceso = new InterfazContableProceso();
/* 133:178 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 134:179 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 135:180 */     this.interfazContableProceso.setFechaDesde(Calendar.getInstance().getTime());
/* 136:181 */     this.interfazContableProceso.setFechaHasta(Calendar.getInstance().getTime());
/* 137:182 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 138:183 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.INTERFAZ_CONSUMOS_BODEGA);
/* 139:184 */     this.interfazContableProceso.setAsiento(new Asiento());
/* 140:185 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String preliminarInterfazProceso()
/* 144:    */   {
/* 145:    */     try
/* 146:    */     {
/* 147:190 */       this.servicioInterfazContableProceso.generarAsiento(this.interfazContableProceso);
/* 148:    */     }
/* 149:    */     catch (ExcepcionAS2Financiero e)
/* 150:    */     {
/* 151:193 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 152:    */     }
/* 153:    */     catch (ExcepcionAS2 e)
/* 154:    */     {
/* 155:195 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 156:196 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 157:    */     }
/* 158:    */     catch (AS2Exception e)
/* 159:    */     {
/* 160:198 */       this.exContabilizacion = e;
/* 161:199 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 162:200 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 163:    */     }
/* 164:202 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String cargarDatos()
/* 168:    */   {
/* 169:207 */     setEditado(false);
/* 170:208 */     limpiar();
/* 171:209 */     return null;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 175:    */   {
/* 176:213 */     List<DetalleAsiento> lista = new ArrayList();
/* 177:214 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 178:215 */       for (DetalleAsiento da : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 179:217 */         if (!da.isEliminado()) {
/* 180:218 */           lista.add(da);
/* 181:    */         }
/* 182:    */       }
/* 183:    */     }
/* 184:222 */     return lista;
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void calcular()
/* 188:    */   {
/* 189:226 */     this.debe = BigDecimal.ZERO;
/* 190:227 */     this.haber = BigDecimal.ZERO;
/* 191:228 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 192:229 */       for (DetalleAsiento d : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 193:230 */         if (!d.isEliminado())
/* 194:    */         {
/* 195:231 */           this.haber = this.haber.add(d.getHaber());
/* 196:232 */           this.debe = this.debe.add(d.getDebe());
/* 197:    */         }
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public DataTable getDtDetalleAsiento()
/* 203:    */   {
/* 204:239 */     return this.dtDetalleAsiento;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 208:    */   {
/* 209:243 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public BigDecimal getDebe()
/* 213:    */   {
/* 214:248 */     calcular();
/* 215:    */     
/* 216:250 */     return this.debe;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDebe(BigDecimal debe)
/* 220:    */   {
/* 221:254 */     this.debe = debe;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public BigDecimal getHaber()
/* 225:    */   {
/* 226:259 */     calcular();
/* 227:    */     
/* 228:261 */     return this.haber;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setHaber(BigDecimal haber)
/* 232:    */   {
/* 233:265 */     this.haber = haber;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public InterfazContableProceso getInterfazContableProceso()
/* 237:    */   {
/* 238:269 */     return this.interfazContableProceso;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 242:    */   {
/* 243:273 */     this.interfazContableProceso = interfazContableProceso;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 247:    */   {
/* 248:277 */     return this.listaInterfazContableProceso;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 252:    */   {
/* 253:281 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public DataTable getDtInterfazContableProceso()
/* 257:    */   {
/* 258:285 */     return this.dtInterfazContableProceso;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 262:    */   {
/* 263:289 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public AS2Exception getExContabilizacion()
/* 267:    */   {
/* 268:298 */     return this.exContabilizacion;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 272:    */   {
/* 273:308 */     this.exContabilizacion = exContabilizacion;
/* 274:    */   }
/* 275:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.InterfazContableConsumoBodegaBean
 * JD-Core Version:    0.7.0.1
 */