/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.DetallePreDevolucionCliente;
/*   7:    */ import com.asinfo.as2.entities.Lote;
/*   8:    */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  12:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  13:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.component.datatable.DataTable;
/*  27:    */ import org.primefaces.model.LazyDataModel;
/*  28:    */ import org.primefaces.model.SortOrder;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class RecepcionDevolucionClienteBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  38:    */   @EJB
/*  39:    */   private transient ServicioGenerico<PreDevolucionCliente> servicioPreDevolucionCliente;
/*  40:    */   @EJB
/*  41:    */   private transient ServicioGenerico<DetallePreDevolucionCliente> servicioDetallePreDevolucionCliente;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioLote servicioLote;
/*  44:    */   private PreDevolucionCliente preDevolucionCliente;
/*  45:    */   private LazyDataModel<PreDevolucionCliente> listaPreDevolucionCliente;
/*  46:    */   private DataTable dtPreDevolucionCliente;
/*  47:    */   private DataTable dtDetallePreDevolucionCliente;
/*  48:    */   private List<Bodega> listaBodega;
/*  49:    */   
/*  50:    */   @PostConstruct
/*  51:    */   public void init()
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55: 72 */       this.listaPreDevolucionCliente = new LazyDataModel()
/*  56:    */       {
/*  57:    */         private static final long serialVersionUID = 1L;
/*  58:    */         
/*  59:    */         public List<PreDevolucionCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  60:    */         {
/*  61: 79 */           List<PreDevolucionCliente> lista = new ArrayList();
/*  62: 80 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  63: 81 */           List<String> lcampos = new ArrayList();
/*  64: 82 */           lcampos.add("empresa.tipoIdentificacion");
/*  65: 83 */           lcampos.add("subempresa.empresa");
/*  66: 84 */           lcampos.add("transportista");
/*  67: 85 */           lista = RecepcionDevolucionClienteBean.this.servicioPreDevolucionCliente.obtenerListaPorPagina(PreDevolucionCliente.class, startIndex, pageSize, sortField, ordenar, filters, lcampos);
/*  68:    */           
/*  69: 87 */           RecepcionDevolucionClienteBean.this.listaPreDevolucionCliente.setRowCount(RecepcionDevolucionClienteBean.this.servicioPreDevolucionCliente.contarPorCriterio(PreDevolucionCliente.class, filters));
/*  70:    */           
/*  71: 89 */           return lista;
/*  72:    */         }
/*  73:    */       };
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77: 94 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  78: 95 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String crear()
/*  83:    */   {
/*  84:101 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  85:102 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String editar()
/*  89:    */   {
/*  90:113 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  91:    */     
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:126 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void cargarDetalle()
/* 107:    */   {
/* 108:134 */     List<String> lcampos = new ArrayList();
/* 109:135 */     lcampos.add("sucursal");
/* 110:136 */     lcampos.add("empresa.tipoIdentificacion");
/* 111:137 */     lcampos.add("subempresa.empresa");
/* 112:138 */     lcampos.add("transportista");
/* 113:139 */     lcampos.add("motivoNotaCreditoCliente");
/* 114:140 */     lcampos.add("listaDetallePreDevolucionCliente");
/* 115:141 */     this.preDevolucionCliente = ((PreDevolucionCliente)this.servicioPreDevolucionCliente.cargarDetalle(PreDevolucionCliente.class, this.preDevolucionCliente.getId(), lcampos));
/* 116:    */     
/* 117:143 */     Map<String, String> filtros = new HashMap();
/* 118:144 */     filtros.put("preDevolucionCliente.idPreDevolucionCliente", "" + this.preDevolucionCliente.getId());
/* 119:145 */     lcampos = new ArrayList();
/* 120:146 */     lcampos.add("producto.categoriaImpuesto");
/* 121:147 */     lcampos.add("unidad");
/* 122:148 */     lcampos.add("bodega");
/* 123:149 */     lcampos.add("lote");
/* 124:150 */     List<DetallePreDevolucionCliente> ldetalle = this.servicioDetallePreDevolucionCliente.obtenerListaPorPagina(DetallePreDevolucionCliente.class, 0, 1000, "idDetallePreDevolucionCliente", true, filtros, lcampos);
/* 125:    */     
/* 126:152 */     this.preDevolucionCliente.setListaDetallePreDevolucionCliente(ldetalle);
/* 127:154 */     if (this.preDevolucionCliente.getFechaRecepcion() == null) {
/* 128:155 */       this.preDevolucionCliente.setFechaRecepcion(new Date());
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String guardar()
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:167 */       this.servicioPreDevolucionCliente.guardarValidar(this.preDevolucionCliente, this.preDevolucionCliente.getListaDetallePreDevolucionCliente());
/* 137:168 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 138:169 */       limpiar();
/* 139:    */     }
/* 140:    */     catch (Exception e)
/* 141:    */     {
/* 142:171 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 143:172 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 144:    */     }
/* 145:175 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String eliminar()
/* 149:    */   {
/* 150:180 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String limpiar()
/* 154:    */   {
/* 155:185 */     setEditado(false);
/* 156:186 */     this.preDevolucionCliente = null;
/* 157:    */     
/* 158:188 */     return "";
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String cargarDatos()
/* 162:    */   {
/* 163:193 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public PreDevolucionCliente getPreDevolucionCliente()
/* 167:    */   {
/* 168:200 */     return this.preDevolucionCliente;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setPreDevolucionCliente(PreDevolucionCliente preDevolucionCliente)
/* 172:    */   {
/* 173:208 */     this.preDevolucionCliente = preDevolucionCliente;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public LazyDataModel<PreDevolucionCliente> getListaPreDevolucionCliente()
/* 177:    */   {
/* 178:215 */     return this.listaPreDevolucionCliente;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setListaPreDevolucionCliente(LazyDataModel<PreDevolucionCliente> listaPreDevolucionCliente)
/* 182:    */   {
/* 183:223 */     this.listaPreDevolucionCliente = listaPreDevolucionCliente;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public DataTable getDtPreDevolucionCliente()
/* 187:    */   {
/* 188:230 */     return this.dtPreDevolucionCliente;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDtPreDevolucionCliente(DataTable dtPreDevolucionCliente)
/* 192:    */   {
/* 193:238 */     this.dtPreDevolucionCliente = dtPreDevolucionCliente;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public DataTable getDtDetallePreDevolucionCliente()
/* 197:    */   {
/* 198:245 */     return this.dtDetallePreDevolucionCliente;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setDtDetallePreDevolucionCliente(DataTable dtDetallePreDevolucionCliente)
/* 202:    */   {
/* 203:253 */     this.dtDetallePreDevolucionCliente = dtDetallePreDevolucionCliente;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void recibirPreDevolucion()
/* 207:    */   {
/* 208:    */     try
/* 209:    */     {
/* 210:258 */       cargarDetalle();
/* 211:259 */       if (this.preDevolucionCliente.getEstado().ordinal() >= 5) {
/* 212:260 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 213:    */       }
/* 214:262 */       for (DetallePreDevolucionCliente dpdc : this.preDevolucionCliente.getListaDetallePreDevolucionCliente())
/* 215:    */       {
/* 216:263 */         if (null == dpdc.getBodega())
/* 217:    */         {
/* 218:264 */           addErrorMessage("Debe ingresar bodega");
/* 219:265 */           return;
/* 220:    */         }
/* 221:267 */         if ((dpdc.getProducto().isIndicadorLote()) && (null == dpdc.getLote()))
/* 222:    */         {
/* 223:268 */           addErrorMessage("Debe ingresar lote");
/* 224:269 */           return;
/* 225:    */         }
/* 226:    */       }
/* 227:272 */       this.servicioNotaCreditoCliente.procesarPreDevolucion(this.preDevolucionCliente, isIndicadorAutoimpresor(), AppUtil.getPuntoDeVenta());
/* 228:    */     }
/* 229:    */     catch (Exception e)
/* 230:    */     {
/* 231:274 */       addErrorMessage(e.getMessage());
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List<Bodega> getListaBodega()
/* 236:    */   {
/* 237:282 */     if (null == this.listaBodega) {
/* 238:283 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 239:    */     }
/* 240:286 */     return this.listaBodega;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 244:    */   {
/* 245:294 */     this.listaBodega = listaBodega;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public List<Lote> autocompletarLotes(String consulta)
/* 249:    */   {
/* 250:298 */     DetallePreDevolucionCliente dpdc = (DetallePreDevolucionCliente)this.dtDetallePreDevolucionCliente.getRowData();
/* 251:    */     
/* 252:300 */     return this.servicioLote.autocompletarLote(dpdc.getProducto(), consulta);
/* 253:    */   }
/* 254:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.RecepcionDevolucionClienteBean
 * JD-Core Version:    0.7.0.1
 */