/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.io.BufferedInputStream;
/*  16:    */ import java.io.InputStream;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.component.datatable.DataTable;
/*  27:    */ import org.primefaces.event.FileUploadEvent;
/*  28:    */ import org.primefaces.model.LazyDataModel;
/*  29:    */ import org.primefaces.model.SortOrder;
/*  30:    */ import org.primefaces.model.UploadedFile;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class AtributoBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -948356706246057599L;
/*  38:    */   @EJB
/*  39:    */   ServicioAtributo servicioAtributo;
/*  40:    */   private Atributo atributo;
/*  41:    */   private LazyDataModel<Atributo> listaAtributo;
/*  42:    */   private List<SelectItem> listaTipoAtributo;
/*  43:    */   private List<ValorAtributo> listaValorAtributoFiltrado;
/*  44:    */   private DataTable dtAtributo;
/*  45:    */   private DataTable dtValorAtributo;
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 70 */     if (this.dtValorAtributo != null) {
/*  51: 71 */       this.dtValorAtributo.reset();
/*  52:    */     }
/*  53: 73 */     this.listaAtributo = new LazyDataModel()
/*  54:    */     {
/*  55:    */       private static final long serialVersionUID = 1L;
/*  56:    */       
/*  57:    */       public List<Atributo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  58:    */       {
/*  59: 80 */         List<Atributo> lista = new ArrayList();
/*  60: 81 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  61:    */         
/*  62: 83 */         lista = AtributoBean.this.servicioAtributo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  63: 84 */         AtributoBean.this.listaAtributo.setRowCount(AtributoBean.this.servicioAtributo.contarPorCriterio(filters));
/*  64:    */         
/*  65: 86 */         return lista;
/*  66:    */       }
/*  67:    */     };
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String editar()
/*  71:    */   {
/*  72: 99 */     if ((this.atributo != null) && (this.atributo.getIdAtributo() != 0))
/*  73:    */     {
/*  74:100 */       this.atributo = this.servicioAtributo.cargarDetalle(this.atributo.getId());
/*  75:101 */       this.listaValorAtributoFiltrado = null;
/*  76:102 */       setEditado(true);
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:104 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  81:    */     }
/*  82:107 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String guardar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:117 */       if ((!this.atributo.isIndicadorCliente()) && (!this.atributo.isIndicadorProveedor()) && (!this.atributo.isIndicadorProducto()))
/*  90:    */       {
/*  91:118 */         addErrorMessage(getLanguageController().getMensaje("msg_error_seleccione_indicador"));
/*  92:119 */         return "";
/*  93:    */       }
/*  94:122 */       this.servicioAtributo.guardar(this.atributo);
/*  95:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  96:124 */       limpiar();
/*  97:125 */       setEditado(false);
/*  98:    */     }
/*  99:    */     catch (Exception e)
/* 100:    */     {
/* 101:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 102:128 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 103:    */     }
/* 104:131 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String eliminar()
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:140 */       this.servicioAtributo.eliminar(this.atributo);
/* 112:141 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 117:144 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 118:    */     }
/* 119:147 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String eliminarDetalle()
/* 123:    */   {
/* 124:151 */     ValorAtributo valorAtributo = (ValorAtributo)this.dtValorAtributo.getRowData();
/* 125:152 */     valorAtributo.setEliminado(true);
/* 126:153 */     this.listaValorAtributoFiltrado = null;
/* 127:154 */     this.dtValorAtributo.reset();
/* 128:    */     
/* 129:156 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String cargarDatos()
/* 133:    */   {
/* 134:164 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void crearAtributo()
/* 138:    */   {
/* 139:173 */     this.atributo = new Atributo();
/* 140:174 */     this.atributo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 141:175 */     this.atributo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 142:176 */     this.atributo.setActivo(true);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void agregarDetalle()
/* 146:    */   {
/* 147:180 */     ValorAtributo valorAtributo = new ValorAtributo();
/* 148:181 */     valorAtributo.setAtributo(this.atributo);
/* 149:182 */     valorAtributo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 150:    */     
/* 151:184 */     this.atributo.getListaValorAtributo().add(valorAtributo);
/* 152:185 */     this.dtValorAtributo.reset();
/* 153:186 */     this.listaValorAtributoFiltrado = null;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String limpiar()
/* 157:    */   {
/* 158:195 */     crearAtributo();
/* 159:196 */     this.dtValorAtributo.reset();
/* 160:197 */     this.listaValorAtributoFiltrado = null;
/* 161:198 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public ServicioAtributo getServicioAtributoBean()
/* 165:    */   {
/* 166:202 */     return this.servicioAtributo;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setServicioAtributoBean(ServicioAtributo servicioAtributo)
/* 170:    */   {
/* 171:206 */     this.servicioAtributo = servicioAtributo;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Atributo getAtributo()
/* 175:    */   {
/* 176:210 */     return this.atributo;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setAtributo(Atributo atributo)
/* 180:    */   {
/* 181:214 */     this.atributo = atributo;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public LazyDataModel<Atributo> getListaAtributos()
/* 185:    */   {
/* 186:218 */     return this.listaAtributo;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setListaAtributos(LazyDataModel<Atributo> listaAtributo)
/* 190:    */   {
/* 191:222 */     this.listaAtributo = listaAtributo;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String cargarDetalleAtributo(FileUploadEvent event)
/* 195:    */   {
/* 196:    */     try
/* 197:    */     {
/* 198:229 */       String fileName = "atributo" + event.getFile().getFileName();
/* 199:230 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 200:231 */       this.servicioAtributo.cargarDetalleAtributo(this.atributo, AppUtil.getOrganizacion().getIdOrganizacion(), fileName, input, 5);
/* 201:232 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 202:    */     }
/* 203:    */     catch (ExcepcionAS2Financiero e)
/* 204:    */     {
/* 205:235 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 206:236 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 207:    */     }
/* 208:    */     catch (ExcepcionAS2Compras e)
/* 209:    */     {
/* 210:239 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 211:240 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 212:    */     }
/* 213:    */     catch (ExcepcionAS2 e)
/* 214:    */     {
/* 215:243 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 216:244 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 217:    */     }
/* 218:    */     catch (Exception e)
/* 219:    */     {
/* 220:247 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 221:    */     }
/* 222:249 */     return null;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<SelectItem> getListaTipoAtributo()
/* 226:    */   {
/* 227:258 */     if (this.listaTipoAtributo == null)
/* 228:    */     {
/* 229:259 */       this.listaTipoAtributo = new ArrayList();
/* 230:261 */       for (TipoAtributo t : TipoAtributo.values())
/* 231:    */       {
/* 232:262 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 233:263 */         this.listaTipoAtributo.add(item);
/* 234:    */       }
/* 235:    */     }
/* 236:266 */     return this.listaTipoAtributo;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setListaTipoAtributo(List<SelectItem> listaTipoAtributo)
/* 240:    */   {
/* 241:276 */     this.listaTipoAtributo = listaTipoAtributo;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public List<ValorAtributo> getListaValorAtributo()
/* 245:    */   {
/* 246:285 */     List<ValorAtributo> lista = new ArrayList();
/* 247:287 */     for (ValorAtributo valorAtributo : this.atributo.getListaValorAtributo()) {
/* 248:288 */       if (!valorAtributo.isEliminado()) {
/* 249:289 */         lista.add(valorAtributo);
/* 250:    */       }
/* 251:    */     }
/* 252:293 */     return lista;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public DataTable getDtValorAtributo()
/* 256:    */   {
/* 257:302 */     return this.dtValorAtributo;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setDtValorAtributo(DataTable dtValorAtributo)
/* 261:    */   {
/* 262:312 */     this.dtValorAtributo = dtValorAtributo;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public DataTable getDtAtributo()
/* 266:    */   {
/* 267:321 */     return this.dtAtributo;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setDtAtributo(DataTable dtAtributo)
/* 271:    */   {
/* 272:331 */     this.dtAtributo = dtAtributo;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public List<ValorAtributo> getListaValorAtributoFiltrado()
/* 276:    */   {
/* 277:338 */     return this.listaValorAtributoFiltrado;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setListaValorAtributoFiltrado(List<ValorAtributo> listaValorAtributoFiltrado)
/* 281:    */   {
/* 282:345 */     this.listaValorAtributoFiltrado = listaValorAtributoFiltrado;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void valorAtributoEditado(ValorAtributo valorAtributo)
/* 286:    */   {
/* 287:349 */     ValorAtributo valorAtributoAux = (ValorAtributo)this.dtValorAtributo.getRowData();
/* 288:350 */     valorAtributoAux.setEditado(true);
/* 289:    */   }
/* 290:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.AtributoBean
 * JD-Core Version:    0.7.0.1
 */