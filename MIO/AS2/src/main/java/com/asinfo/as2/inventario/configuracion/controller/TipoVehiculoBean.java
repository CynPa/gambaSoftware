/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Ciudad;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Ruta;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.TipoVehiculo;
/*  10:    */ import com.asinfo.as2.enumeraciones.FormaPagoFleteEnum;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoVehiculo;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.JsfUtil;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ import org.primefaces.model.TreeNode;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class TipoVehiculoBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @EJB
/*  37:    */   private ServicioTipoVehiculo servicioTipoVehiculo;
/*  38:    */   private TipoVehiculo tipoVehiculo;
/*  39:    */   private LazyDataModel<TipoVehiculo> listaTipoVehiculo;
/*  40:    */   private Ruta rutaSeleccionada;
/*  41:    */   private List<FormaPagoFleteEnum> listaFormaPagoFlete;
/*  42:    */   private TreeNode selectedNode;
/*  43:    */   private enumCiudadEditada ciudadEditada;
/*  44:    */   private Ciudad ciudad;
/*  45:    */   private DataTable dtTipoVehiculo;
/*  46:    */   private DataTable dtRuta;
/*  47:    */   
/*  48:    */   private static enum enumCiudadEditada
/*  49:    */   {
/*  50: 60 */     CIUDAD_ORIGEN,  CIUDAD_DESTINO;
/*  51:    */     
/*  52:    */     private enumCiudadEditada() {}
/*  53:    */   }
/*  54:    */   
/*  55:    */   @PostConstruct
/*  56:    */   public void init()
/*  57:    */   {
/*  58: 73 */     this.listaTipoVehiculo = new LazyDataModel()
/*  59:    */     {
/*  60:    */       private static final long serialVersionUID = 1L;
/*  61:    */       
/*  62:    */       public List<TipoVehiculo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  63:    */       {
/*  64: 79 */         List<TipoVehiculo> lista = new ArrayList();
/*  65:    */         
/*  66: 81 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  67: 82 */         lista = TipoVehiculoBean.this.servicioTipoVehiculo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  68:    */         
/*  69: 84 */         TipoVehiculoBean.this.listaTipoVehiculo.setRowCount(TipoVehiculoBean.this.servicioTipoVehiculo.contarPorCriterio(filters));
/*  70:    */         
/*  71: 86 */         return lista;
/*  72:    */       }
/*  73:    */     };
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String editar()
/*  77:    */   {
/*  78: 94 */     if (getTipoVehiculo().getId() > 0) {
/*  79:    */       try
/*  80:    */       {
/*  81: 97 */         this.tipoVehiculo = this.servicioTipoVehiculo.cargarDetalle(this.tipoVehiculo.getId());
/*  82: 98 */         setEditado(true);
/*  83:    */       }
/*  84:    */       catch (ExcepcionAS2 e)
/*  85:    */       {
/*  86:101 */         LOG.error("ERROR AL CARGAR DATOS" + e);
/*  87:102 */         e.printStackTrace();
/*  88:    */       }
/*  89:    */     } else {
/*  90:105 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  91:    */     }
/*  92:107 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void crearTipoVehiculo()
/*  96:    */   {
/*  97:111 */     this.tipoVehiculo = new TipoVehiculo();
/*  98:112 */     this.tipoVehiculo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  99:113 */     this.tipoVehiculo.setIdSucursal(AppUtil.getSucursal().getId());
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String guardar()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:119 */       this.servicioTipoVehiculo.validarWarning(this.tipoVehiculo);
/* 107:    */     }
/* 108:    */     catch (AS2Exception e)
/* 109:    */     {
/* 110:121 */       JsfUtil.addErrorMessage(e, "");
/* 111:    */     }
/* 112:    */     try
/* 113:    */     {
/* 114:124 */       this.servicioTipoVehiculo.guardar(this.tipoVehiculo);
/* 115:125 */       limpiar();
/* 116:126 */       setEditado(false);
/* 117:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 118:    */     }
/* 119:    */     catch (AS2Exception e)
/* 120:    */     {
/* 121:129 */       JsfUtil.addErrorMessage(e, "");
/* 122:    */     }
/* 123:    */     catch (Exception e)
/* 124:    */     {
/* 125:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 126:132 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 127:    */     }
/* 128:134 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String eliminar()
/* 132:    */   {
/* 133:    */     try
/* 134:    */     {
/* 135:141 */       this.servicioTipoVehiculo.eliminar(getTipoVehiculo());
/* 136:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 137:    */     }
/* 138:    */     catch (Exception e)
/* 139:    */     {
/* 140:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 141:145 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 142:    */     }
/* 143:147 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String limpiar()
/* 147:    */   {
/* 148:152 */     crearTipoVehiculo();
/* 149:153 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String cargarDatos()
/* 153:    */   {
/* 154:158 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String agregarDetalle()
/* 158:    */   {
/* 159:162 */     Ruta ruta = new Ruta();
/* 160:163 */     ruta.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 161:164 */     ruta.setIdSucursal(AppUtil.getSucursal().getId());
/* 162:165 */     ruta.setTipoVehiculo(getTipoVehiculo());
/* 163:166 */     ruta.setTarifa(BigDecimal.ZERO);
/* 164:167 */     ruta.setCiudadDestino(new Ciudad());
/* 165:168 */     ruta.setCiudadOrigen(new Ciudad());
/* 166:169 */     getTipoVehiculo().getListaRuta().add(ruta);
/* 167:170 */     return "";
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String eliminarDetalle()
/* 171:    */   {
/* 172:174 */     Ruta ruta = (Ruta)this.dtRuta.getRowData();
/* 173:175 */     ruta.setEliminado(true);
/* 174:176 */     return "";
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void actualizarCiudadOrigen()
/* 178:    */   {
/* 179:180 */     this.rutaSeleccionada = ((Ruta)this.dtRuta.getRowData());
/* 180:181 */     this.ciudadEditada = enumCiudadEditada.CIUDAD_ORIGEN;
/* 181:182 */     this.ciudad = this.rutaSeleccionada.getCiudadOrigen();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void actualizarCiudadDestino()
/* 185:    */   {
/* 186:186 */     this.rutaSeleccionada = ((Ruta)this.dtRuta.getRowData());
/* 187:187 */     this.ciudadEditada = enumCiudadEditada.CIUDAD_DESTINO;
/* 188:188 */     this.ciudad = this.rutaSeleccionada.getCiudadDestino();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void cargarCiudad()
/* 192:    */   {
/* 193:    */     try
/* 194:    */     {
/* 195:193 */       switch (2.$SwitchMap$com$asinfo$as2$inventario$configuracion$controller$TipoVehiculoBean$enumCiudadEditada[this.ciudadEditada.ordinal()])
/* 196:    */       {
/* 197:    */       case 1: 
/* 198:195 */         this.ciudad = ((Ciudad)this.selectedNode.getData());
/* 199:196 */         this.rutaSeleccionada.setCiudadOrigen(this.ciudad);
/* 200:197 */         this.servicioTipoVehiculo.validarWarning(this.tipoVehiculo);
/* 201:198 */         break;
/* 202:    */       case 2: 
/* 203:200 */         this.ciudad = ((Ciudad)this.selectedNode.getData());
/* 204:201 */         this.rutaSeleccionada.setCiudadDestino(this.ciudad);
/* 205:202 */         this.servicioTipoVehiculo.validarWarning(this.tipoVehiculo);
/* 206:    */       }
/* 207:    */     }
/* 208:    */     catch (AS2Exception e)
/* 209:    */     {
/* 210:208 */       JsfUtil.addErrorMessage(e, "");
/* 211:    */     }
/* 212:    */     catch (Exception e)
/* 213:    */     {
/* 214:210 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public TipoVehiculo getTipoVehiculo()
/* 219:    */   {
/* 220:216 */     if (this.tipoVehiculo == null) {
/* 221:217 */       crearTipoVehiculo();
/* 222:    */     }
/* 223:219 */     return this.tipoVehiculo;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setTipoVehiculo(TipoVehiculo tipoVehiculo)
/* 227:    */   {
/* 228:223 */     this.tipoVehiculo = tipoVehiculo;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public LazyDataModel<TipoVehiculo> getListaTipoVehiculo()
/* 232:    */   {
/* 233:227 */     return this.listaTipoVehiculo;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setListaTipoVehiculo(LazyDataModel<TipoVehiculo> listaTipoVehiculo)
/* 237:    */   {
/* 238:231 */     this.listaTipoVehiculo = listaTipoVehiculo;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public List<Ruta> getListaRuta()
/* 242:    */   {
/* 243:235 */     List<Ruta> rutas = new ArrayList();
/* 244:236 */     for (Ruta r : getTipoVehiculo().getListaRuta()) {
/* 245:237 */       if (!r.isEliminado()) {
/* 246:238 */         rutas.add(r);
/* 247:    */       }
/* 248:    */     }
/* 249:241 */     return rutas;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public DataTable getDtTipoVehiculo()
/* 253:    */   {
/* 254:245 */     return this.dtTipoVehiculo;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setDtTipoVehiculo(DataTable dtTipoVehiculo)
/* 258:    */   {
/* 259:249 */     this.dtTipoVehiculo = dtTipoVehiculo;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public DataTable getDtRuta()
/* 263:    */   {
/* 264:253 */     return this.dtRuta;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setDtRuta(DataTable dtRuta)
/* 268:    */   {
/* 269:257 */     this.dtRuta = dtRuta;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Ruta getRutaSeleccionada()
/* 273:    */   {
/* 274:261 */     return this.rutaSeleccionada;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setRutaSeleccionada(Ruta rutaSeleccionada)
/* 278:    */   {
/* 279:265 */     this.rutaSeleccionada = rutaSeleccionada;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public TreeNode getSelectedNode()
/* 283:    */   {
/* 284:269 */     return this.selectedNode;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setSelectedNode(TreeNode selectedNode)
/* 288:    */   {
/* 289:273 */     this.selectedNode = selectedNode;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public enumCiudadEditada getCiudadEditada()
/* 293:    */   {
/* 294:277 */     return this.ciudadEditada;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setCiudadEditada(enumCiudadEditada ciudadEditada)
/* 298:    */   {
/* 299:281 */     this.ciudadEditada = ciudadEditada;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public Ciudad getCiudad()
/* 303:    */   {
/* 304:285 */     return this.ciudad;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setCiudad(Ciudad ciudad)
/* 308:    */   {
/* 309:289 */     this.ciudad = ciudad;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public List<FormaPagoFleteEnum> getListaFormaPagoFlete()
/* 313:    */   {
/* 314:293 */     if (this.listaFormaPagoFlete == null)
/* 315:    */     {
/* 316:294 */       this.listaFormaPagoFlete = new ArrayList();
/* 317:295 */       this.listaFormaPagoFlete.add(FormaPagoFleteEnum.PAGA_X_NUMERO_FLETES);
/* 318:296 */       this.listaFormaPagoFlete.add(FormaPagoFleteEnum.PAGA_X_PESO);
/* 319:    */     }
/* 320:298 */     return this.listaFormaPagoFlete;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setListaFormaPagoFlete(List<FormaPagoFleteEnum> listaFormaPagoFlete)
/* 324:    */   {
/* 325:302 */     this.listaFormaPagoFlete = listaFormaPagoFlete;
/* 326:    */   }
/* 327:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.TipoVehiculoBean
 * JD-Core Version:    0.7.0.1
 */