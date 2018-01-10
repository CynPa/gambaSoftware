/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.CajaChica;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  14:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCajaChica;
/*  18:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.component.datatable.DataTable;
/*  30:    */ import org.primefaces.model.LazyDataModel;
/*  31:    */ import org.primefaces.model.SortOrder;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class CajaChicaBean
/*  36:    */   extends PageControllerAS2
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @EJB
/*  40:    */   private ServicioCajaChica servicioCajaChica;
/*  41:    */   @EJB
/*  42:    */   private ServicioCuentaContable servicioCuentaContable;
/*  43:    */   @EJB
/*  44:    */   private ServicioDocumento servicioDocumento;
/*  45:    */   private CajaChica cajaChica;
/*  46:    */   private LazyDataModel<CajaChica> listaCajaChica;
/*  47:    */   private List<Documento> listaDocumento;
/*  48:    */   private List<CuentaContable> listaCuentaContable;
/*  49:    */   private DataTable dtCajaChica;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 94 */     crearCajaChica();
/*  55: 95 */     this.listaCajaChica = new LazyDataModel()
/*  56:    */     {
/*  57:    */       private static final long serialVersionUID = 1L;
/*  58:    */       
/*  59:    */       public List<CajaChica> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  60:    */       {
/*  61:102 */         List<CajaChica> lista = new ArrayList();
/*  62:    */         
/*  63:104 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  64:    */         
/*  65:106 */         lista = CajaChicaBean.this.servicioCajaChica.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  66:107 */         CajaChicaBean.this.listaCajaChica.setRowCount(CajaChicaBean.this.servicioCajaChica.contarPorCriterio(filters));
/*  67:    */         
/*  68:109 */         return lista;
/*  69:    */       }
/*  70:    */     };
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String crearCajaChica()
/*  74:    */   {
/*  75:121 */     this.cajaChica = new CajaChica();
/*  76:122 */     this.cajaChica.setEstado(Estado.ELABORADO);
/*  77:123 */     this.cajaChica.setDocumento(new Documento());
/*  78:124 */     this.cajaChica.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  79:125 */     this.cajaChica.setSucursal(AppUtil.getSucursal());
/*  80:    */     
/*  81:127 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String guardar()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:139 */       this.servicioCajaChica.guardar(this.cajaChica);
/*  89:140 */       setEditado(false);
/*  90:    */       
/*  91:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  92:    */       
/*  93:144 */       limpiar();
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:147 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  98:148 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  99:    */     }
/* 100:150 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String eliminar()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:161 */       this.servicioCajaChica.eliminar(this.cajaChica);
/* 108:162 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 109:163 */       cargarDatos();
/* 110:    */     }
/* 111:    */     catch (ExcepcionAS2Financiero e)
/* 112:    */     {
/* 113:165 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 118:168 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 119:    */     }
/* 120:170 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String cargarDatos()
/* 124:    */   {
/* 125:180 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String editar()
/* 129:    */   {
/* 130:190 */     if (getCajaChica().getId() > 0) {
/* 131:    */       try
/* 132:    */       {
/* 133:192 */         this.servicioCajaChica.esEditable(this.cajaChica);
/* 134:193 */         this.cajaChica = this.servicioCajaChica.cargarDetalle(getCajaChica().getId());
/* 135:194 */         setEditado(true);
/* 136:    */       }
/* 137:    */       catch (ExcepcionAS2Financiero e)
/* 138:    */       {
/* 139:196 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 140:197 */         LOG.error("ERROR AL EDITAR DATOS", e);
/* 141:    */       }
/* 142:    */       catch (Exception e)
/* 143:    */       {
/* 144:199 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 145:200 */         LOG.error("ERROR AL EDITAR DATOS", e);
/* 146:    */       }
/* 147:    */     } else {
/* 148:203 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 149:    */     }
/* 150:206 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String limpiar()
/* 154:    */   {
/* 155:216 */     this.cajaChica = null;
/* 156:217 */     crearCajaChica();
/* 157:218 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String liquidarCajaChica()
/* 161:    */   {
/* 162:223 */     String pagina = "";
/* 163:224 */     CajaChica c = (CajaChica)this.dtCajaChica.getRowData();
/* 164:    */     try
/* 165:    */     {
/* 166:226 */       this.servicioCajaChica.esEditable(c);
/* 167:227 */       this.servicioCajaChica.verificaEmisionRetencion(c);
/* 168:228 */       pagina = "liquidacionCajaChica?faces-redirect=true";
/* 169:    */     }
/* 170:    */     catch (ExcepcionAS2Financiero e)
/* 171:    */     {
/* 172:231 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 173:    */     }
/* 174:    */     catch (ExcepcionAS2 e)
/* 175:    */     {
/* 176:233 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 177:    */     }
/* 178:236 */     if (c.getEstado().equals(Estado.ANULADO))
/* 179:    */     {
/* 180:237 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 181:238 */       return "";
/* 182:    */     }
/* 183:240 */     AppUtil.removeAtributo("caja_chica");
/* 184:241 */     AppUtil.setAtributo("caja_chica", c);
/* 185:242 */     return pagina;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void actualizarValorCajaChica()
/* 189:    */   {
/* 190:248 */     this.cajaChica.setValor(this.servicioCuentaContable.obtenerSaldo(FuncionesUtiles.obtenerFechaInicial(), this.cajaChica.getFechaDesde(), this.cajaChica
/* 191:249 */       .getCuentaContable().getIdCuentaContable(), ValoresCalculo.DEBE_HABER, TipoCalculo.SALDO_FINAL, false, 0));
/* 192:250 */     this.cajaChica.setSaldo(this.cajaChica.getValor());
/* 193:    */   }
/* 194:    */   
/* 195:    */   public CajaChica getCajaChica()
/* 196:    */   {
/* 197:258 */     if (this.cajaChica == null) {
/* 198:259 */       crearCajaChica();
/* 199:    */     }
/* 200:261 */     return this.cajaChica;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setCajaChica(CajaChica cajaChica)
/* 204:    */   {
/* 205:265 */     this.cajaChica = cajaChica;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public DataTable getDtCajaChica()
/* 209:    */   {
/* 210:269 */     return this.dtCajaChica;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setDtCajaChica(DataTable dtCajaChica)
/* 214:    */   {
/* 215:273 */     this.dtCajaChica = dtCajaChica;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public ServicioCajaChica getServicioCajaChica()
/* 219:    */   {
/* 220:277 */     return this.servicioCajaChica;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public ServicioCuentaContable getServicioCuentaContable()
/* 224:    */   {
/* 225:281 */     return this.servicioCuentaContable;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<Documento> getListaDocumento()
/* 229:    */   {
/* 230:285 */     if (this.listaDocumento == null) {
/* 231:    */       try
/* 232:    */       {
/* 233:287 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.CAJA_CHICA);
/* 234:    */       }
/* 235:    */       catch (ExcepcionAS2 e)
/* 236:    */       {
/* 237:289 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 238:    */       }
/* 239:    */     }
/* 240:293 */     return this.listaDocumento;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 244:    */   {
/* 245:297 */     this.listaDocumento = listaDocumento;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public List<CuentaContable> getListaCuentaContable()
/* 249:    */   {
/* 250:301 */     if (this.listaCuentaContable == null) {
/* 251:302 */       this.listaCuentaContable = this.servicioCuentaContable.buscarPorTipo(TipoCuentaContable.CAJA_CHICA, AppUtil.getOrganizacion().getIdOrganizacion());
/* 252:    */     }
/* 253:305 */     return this.listaCuentaContable;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setListaCuentaContable(List<CuentaContable> listaCuentaContable)
/* 257:    */   {
/* 258:309 */     this.listaCuentaContable = listaCuentaContable;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public LazyDataModel<CajaChica> getListaCajaChica()
/* 262:    */   {
/* 263:313 */     return this.listaCajaChica;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setListaCajaChica(LazyDataModel<CajaChica> listaCajaChica)
/* 267:    */   {
/* 268:317 */     this.listaCajaChica = listaCajaChica;
/* 269:    */   }
/* 270:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.CajaChicaBean
 * JD-Core Version:    0.7.0.1
 */