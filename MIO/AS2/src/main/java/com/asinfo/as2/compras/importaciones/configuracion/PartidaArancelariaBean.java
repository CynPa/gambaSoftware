/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioPartidaArancelaria;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.DetallePartidaArancelaria;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.PartidaArancelaria;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
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
/*  27:    */ public class PartidaArancelariaBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioPartidaArancelaria servicioPartidaArancelaria;
/*  33:    */   @EJB
/*  34:    */   private ServicioProducto servicioProducto;
/*  35:    */   private PartidaArancelaria partidaArancelaria;
/*  36:    */   private LazyDataModel<PartidaArancelaria> listaPartidaArancelaria;
/*  37:    */   private DataTable dtPartidaArancelaria;
/*  38:    */   private DataTable dtDetalleProducto;
/*  39:    */   private DataTable dtDetallePartidaArancelaria;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 79 */     this.listaPartidaArancelaria = new LazyDataModel()
/*  45:    */     {
/*  46:    */       private static final long serialVersionUID = 1312949801168865877L;
/*  47:    */       
/*  48:    */       public List<PartidaArancelaria> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 88 */         List<PartidaArancelaria> lista = new ArrayList();
/*  51:    */         
/*  52: 90 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  53:    */         
/*  54: 92 */         lista = PartidaArancelariaBean.this.servicioPartidaArancelaria.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  55:    */         
/*  56: 94 */         PartidaArancelariaBean.this.listaPartidaArancelaria.setRowCount(PartidaArancelariaBean.this.servicioPartidaArancelaria.contarPorCriterio(filters));
/*  57:    */         
/*  58: 96 */         return lista;
/*  59:    */       }
/*  60:    */     };
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void crearPartidaArancelaria()
/*  64:    */   {
/*  65:110 */     this.partidaArancelaria = new PartidaArancelaria();
/*  66:111 */     this.partidaArancelaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  67:112 */     this.partidaArancelaria.setIdSucursal(AppUtil.getSucursal().getId());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String editar()
/*  71:    */   {
/*  72:121 */     if (getPartidaArancelaria().getIdPartidaArancelaria() > 0)
/*  73:    */     {
/*  74:122 */       this.partidaArancelaria = this.servicioPartidaArancelaria.cargarDetalle(getPartidaArancelaria().getId());
/*  75:123 */       setEditado(true);
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  80:    */     }
/*  81:127 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String guardar()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:137 */       this.servicioPartidaArancelaria.guardar(this.partidaArancelaria);
/*  89:138 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  90:139 */       setEditado(false);
/*  91:140 */       limpiar();
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  96:143 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  97:    */     }
/*  98:145 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String eliminar()
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:155 */       this.servicioPartidaArancelaria.eliminar(this.partidaArancelaria);
/* 106:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 107:    */     }
/* 108:    */     catch (Exception e)
/* 109:    */     {
/* 110:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 111:159 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 112:    */     }
/* 113:161 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String cargarDatos()
/* 117:    */   {
/* 118:170 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String limpiar()
/* 122:    */   {
/* 123:179 */     crearPartidaArancelaria();
/* 124:180 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<DetallePartidaArancelaria> getListaDetallePartidaArancelaria()
/* 128:    */   {
/* 129:191 */     List<DetallePartidaArancelaria> lista = new ArrayList();
/* 130:192 */     for (DetallePartidaArancelaria detallePartidaArancelaria : getPartidaArancelaria().getListaDetallePartidaArancelaria()) {
/* 131:193 */       if (!detallePartidaArancelaria.isEliminado()) {
/* 132:194 */         lista.add(detallePartidaArancelaria);
/* 133:    */       }
/* 134:    */     }
/* 135:197 */     return lista;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String agregarDetalle()
/* 139:    */   {
/* 140:206 */     DetallePartidaArancelaria detallePartidaArancelaria = new DetallePartidaArancelaria();
/* 141:207 */     detallePartidaArancelaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 142:208 */     detallePartidaArancelaria.setIdSucursal(AppUtil.getSucursal().getId());
/* 143:209 */     detallePartidaArancelaria.setPartidaArancelaria(getPartidaArancelaria());
/* 144:210 */     getPartidaArancelaria().getListaDetallePartidaArancelaria().add(detallePartidaArancelaria);
/* 145:211 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<Producto> getListaDetalleProducto()
/* 149:    */   {
/* 150:220 */     List<Producto> lista = new ArrayList();
/* 151:221 */     for (Producto producto : getPartidaArancelaria().getListaProducto()) {
/* 152:222 */       if (!producto.isEliminado()) {
/* 153:223 */         lista.add(producto);
/* 154:    */       }
/* 155:    */     }
/* 156:226 */     return lista;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String eliminarDetalle()
/* 160:    */   {
/* 161:230 */     DetallePartidaArancelaria detallePartidaArancelaria = (DetallePartidaArancelaria)this.dtDetallePartidaArancelaria.getRowData();
/* 162:231 */     detallePartidaArancelaria.setEliminado(true);
/* 163:232 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void cargarProducto(Producto producto)
/* 167:    */   {
/* 168:243 */     if (producto.getPartidaArancelaria() == null)
/* 169:    */     {
/* 170:244 */       producto = this.servicioProducto.cargaDetalle(producto.getId());
/* 171:245 */       producto.setPartidaArancelaria(getPartidaArancelaria());
/* 172:246 */       getPartidaArancelaria().getListaProducto().add(producto);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String eliminarDetalleProducto()
/* 177:    */   {
/* 178:256 */     Producto producto = (Producto)this.dtDetalleProducto.getRowData();
/* 179:257 */     producto.setEliminado(true);
/* 180:258 */     return "";
/* 181:    */   }
/* 182:    */   
/* 183:    */   public PartidaArancelaria getPartidaArancelaria()
/* 184:    */   {
/* 185:277 */     if (this.partidaArancelaria == null) {
/* 186:278 */       crearPartidaArancelaria();
/* 187:    */     }
/* 188:280 */     return this.partidaArancelaria;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setPartidaArancelaria(PartidaArancelaria partidaArancelaria)
/* 192:    */   {
/* 193:290 */     this.partidaArancelaria = partidaArancelaria;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public LazyDataModel<PartidaArancelaria> getListaPartidaArancelaria()
/* 197:    */   {
/* 198:299 */     return this.listaPartidaArancelaria;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setListaPartidaArancelaria(LazyDataModel<PartidaArancelaria> listaPartidaArancelaria)
/* 202:    */   {
/* 203:309 */     this.listaPartidaArancelaria = listaPartidaArancelaria;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public DataTable getDtPartidaArancelaria()
/* 207:    */   {
/* 208:318 */     return this.dtPartidaArancelaria;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setDtPartidaArancelaria(DataTable dtPartidaArancelaria)
/* 212:    */   {
/* 213:328 */     this.dtPartidaArancelaria = dtPartidaArancelaria;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public DataTable getDtDetalleProducto()
/* 217:    */   {
/* 218:337 */     return this.dtDetalleProducto;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setDtDetalleProducto(DataTable dtDetalleProducto)
/* 222:    */   {
/* 223:347 */     this.dtDetalleProducto = dtDetalleProducto;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public DataTable getDtDetallePartidaArancelaria()
/* 227:    */   {
/* 228:356 */     return this.dtDetallePartidaArancelaria;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setDtDetallePartidaArancelaria(DataTable dtDetallePartidaArancelaria)
/* 232:    */   {
/* 233:366 */     this.dtDetallePartidaArancelaria = dtDetallePartidaArancelaria;
/* 234:    */   }
/* 235:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.PartidaArancelariaBean
 * JD-Core Version:    0.7.0.1
 */