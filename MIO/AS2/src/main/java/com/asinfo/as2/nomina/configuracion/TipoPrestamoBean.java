/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Rubro;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.TipoPrestamo;
/*  12:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  16:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoPrestamo;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.JsfUtil;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.component.datatable.DataTable;
/*  28:    */ import org.primefaces.model.LazyDataModel;
/*  29:    */ import org.primefaces.model.SortOrder;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class TipoPrestamoBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioTipoPrestamo servicioTipoPrestamo;
/*  39:    */   @EJB
/*  40:    */   private ServicioRubro servicioRubro;
/*  41:    */   @EJB
/*  42:    */   private ServicioDocumento servicioDocumento;
/*  43:    */   private TipoPrestamo tipoPrestamo;
/*  44:    */   private String rubroSelecionado;
/*  45:    */   private CuentaContable cuentaContable;
/*  46:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  47:    */   private LazyDataModel<TipoPrestamo> listaTipoPrestamo;
/*  48:    */   private List<Rubro> listaRubro;
/*  49:    */   private List<Documento> listaDocumento;
/*  50:    */   private DataTable dtTipoPrestamo;
/*  51:    */   private DataTable dtCuentaContable;
/*  52:    */   
/*  53:    */   private static enum enumCuentaContableEditada
/*  54:    */   {
/*  55: 73 */     CUENTA_CONTABLE;
/*  56:    */     
/*  57:    */     private enumCuentaContableEditada() {}
/*  58:    */   }
/*  59:    */   
/*  60:    */   @PostConstruct
/*  61:    */   public void init()
/*  62:    */   {
/*  63: 98 */     this.listaTipoPrestamo = new LazyDataModel()
/*  64:    */     {
/*  65:    */       private static final long serialVersionUID = 1L;
/*  66:    */       
/*  67:    */       public List<TipoPrestamo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  68:    */       {
/*  69:105 */         List<TipoPrestamo> lista = new ArrayList();
/*  70:106 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  71:    */         
/*  72:108 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  73:109 */         lista = TipoPrestamoBean.this.servicioTipoPrestamo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  74:    */         
/*  75:111 */         TipoPrestamoBean.this.listaTipoPrestamo.setRowCount(TipoPrestamoBean.this.servicioTipoPrestamo.contarPorCriterio(filters));
/*  76:    */         
/*  77:113 */         return lista;
/*  78:    */       }
/*  79:    */     };
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void crearTipoPrestamo()
/*  83:    */   {
/*  84:127 */     this.tipoPrestamo = new TipoPrestamo();
/*  85:128 */     this.tipoPrestamo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  86:129 */     this.tipoPrestamo.setIdSucursal(AppUtil.getSucursal().getId());
/*  87:130 */     this.tipoPrestamo.setDocumento(new Documento());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String editar()
/*  91:    */   {
/*  92:139 */     if ((getTipoPrestamo() != null) && (getTipoPrestamo().getIdTipoPrestamo() != 0))
/*  93:    */     {
/*  94:140 */       setTipoPrestamo(this.servicioTipoPrestamo.cargarDetalle(this.tipoPrestamo.getId()));
/*  95:141 */       setEditado(true);
/*  96:    */     }
/*  97:    */     else
/*  98:    */     {
/*  99:143 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 100:    */     }
/* 101:145 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String guardar()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:155 */       this.servicioTipoPrestamo.guardar(this.tipoPrestamo);
/* 109:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 110:157 */       setEditado(false);
/* 111:158 */       limpiar();
/* 112:    */     }
/* 113:    */     catch (AS2Exception e)
/* 114:    */     {
/* 115:160 */       JsfUtil.addErrorMessage(e, "");
/* 116:    */     }
/* 117:    */     catch (Exception e)
/* 118:    */     {
/* 119:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 120:163 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 121:    */     }
/* 122:165 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String eliminar()
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:175 */       this.servicioTipoPrestamo.eliminar(this.tipoPrestamo);
/* 130:176 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:178 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 135:179 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 136:    */     }
/* 137:181 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String cargarDatos()
/* 141:    */   {
/* 142:190 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String limpiar()
/* 146:    */   {
/* 147:199 */     crearTipoPrestamo();
/* 148:200 */     return "";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String actualizarDocumento()
/* 152:    */   {
/* 153:204 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getTipoPrestamo().getDocumento().getIdDocumento()));
/* 154:205 */     getTipoPrestamo().setDocumento(documento);
/* 155:    */     
/* 156:207 */     return "";
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<Documento> getListaDocumento()
/* 160:    */   {
/* 161:211 */     if (this.listaDocumento == null) {
/* 162:    */       try
/* 163:    */       {
/* 164:213 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PRESTAMO_NOMINA);
/* 165:    */       }
/* 166:    */       catch (ExcepcionAS2 e)
/* 167:    */       {
/* 168:215 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 169:    */       }
/* 170:    */     }
/* 171:218 */     return this.listaDocumento;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void actualizarCuenta()
/* 175:    */   {
/* 176:225 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE;
/* 177:226 */     this.cuentaContable = this.tipoPrestamo.getCuentaContable();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void cargarCuentaContable()
/* 181:    */   {
/* 182:230 */     this.cuentaContable = ((CuentaContable)this.dtCuentaContable.getRowData());
/* 183:231 */     this.tipoPrestamo.setCuentaContable(this.cuentaContable);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public TipoPrestamo getTipoPrestamo()
/* 187:    */   {
/* 188:244 */     if (this.tipoPrestamo == null) {
/* 189:245 */       this.tipoPrestamo = new TipoPrestamo();
/* 190:    */     }
/* 191:247 */     return this.tipoPrestamo;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setTipoPrestamo(TipoPrestamo tipoPrestamo)
/* 195:    */   {
/* 196:257 */     this.tipoPrestamo = tipoPrestamo;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public LazyDataModel<TipoPrestamo> getListaTipoPrestamo()
/* 200:    */   {
/* 201:266 */     return this.listaTipoPrestamo;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setListaTipoPrestamo(LazyDataModel<TipoPrestamo> listaTipoPrestamo)
/* 205:    */   {
/* 206:276 */     this.listaTipoPrestamo = listaTipoPrestamo;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public DataTable getDtTipoPrestamo()
/* 210:    */   {
/* 211:285 */     return this.dtTipoPrestamo;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setDtTipoPrestamo(DataTable dtTipoPrestamo)
/* 215:    */   {
/* 216:295 */     this.dtTipoPrestamo = dtTipoPrestamo;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public String getRubroSelecionado()
/* 220:    */   {
/* 221:304 */     this.rubroSelecionado = null;
/* 222:305 */     if (getTipoPrestamo().getRubro() != null) {
/* 223:306 */       this.rubroSelecionado = ("" + getTipoPrestamo().getRubro().getId());
/* 224:    */     }
/* 225:308 */     return this.rubroSelecionado;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setRubroSelecionado(String rubroSelecionado)
/* 229:    */   {
/* 230:319 */     if (this.rubroSelecionado != rubroSelecionado)
/* 231:    */     {
/* 232:320 */       this.rubroSelecionado = rubroSelecionado;
/* 233:321 */       Rubro auxRubroSelecionado = null;
/* 234:322 */       if (this.rubroSelecionado != "")
/* 235:    */       {
/* 236:323 */         int idRubroSelecionado = Integer.parseInt(this.rubroSelecionado);
/* 237:324 */         auxRubroSelecionado = this.servicioRubro.buscarPorId(idRubroSelecionado);
/* 238:    */       }
/* 239:326 */       getTipoPrestamo().setRubro(auxRubroSelecionado);
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public List<Rubro> getListaRubro()
/* 244:    */   {
/* 245:336 */     if (this.listaRubro == null) {
/* 246:337 */       this.listaRubro = this.servicioRubro.obtenerListaCombo("nombre", true, null);
/* 247:    */     }
/* 248:339 */     return this.listaRubro;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public CuentaContable getCuentaContable()
/* 252:    */   {
/* 253:348 */     return this.cuentaContable;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 257:    */   {
/* 258:358 */     this.cuentaContable = cuentaContable;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public enumCuentaContableEditada getCuentaContableEditada()
/* 262:    */   {
/* 263:367 */     return this.cuentaContableEditada;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setCuentaContableEditada(enumCuentaContableEditada cuentaContableEditada)
/* 267:    */   {
/* 268:377 */     this.cuentaContableEditada = cuentaContableEditada;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public DataTable getDtCuentaContable()
/* 272:    */   {
/* 273:386 */     return this.dtCuentaContable;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 277:    */   {
/* 278:396 */     this.dtCuentaContable = dtCuentaContable;
/* 279:    */   }
/* 280:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.TipoPrestamoBean
 * JD-Core Version:    0.7.0.1
 */