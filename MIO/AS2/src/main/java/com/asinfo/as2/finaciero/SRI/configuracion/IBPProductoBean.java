/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.sri.IBPCapacidad;
/*   8:    */ import com.asinfo.as2.entities.sri.IBPClasificacion;
/*   9:    */ import com.asinfo.as2.entities.sri.IBPMarca;
/*  10:    */ import com.asinfo.as2.entities.sri.IBPUnidad;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  12:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  13:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.JsfUtil;
/*  16:    */ import java.io.Serializable;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class IBPProductoBean
/*  30:    */   extends PageControllerAS2
/*  31:    */   implements Serializable
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private ServicioGenerico<Producto> servicioProducto;
/*  36:    */   @EJB
/*  37:    */   private ServicioGenerico<IBPClasificacion> servicioIBPClasificacion;
/*  38:    */   @EJB
/*  39:    */   private ServicioGenerico<IBPMarca> servicioIBPMarca;
/*  40:    */   @EJB
/*  41:    */   private ServicioGenerico<IBPCapacidad> servicioIBPCapacidad;
/*  42:    */   @EJB
/*  43:    */   private ServicioGenerico<IBPUnidad> servicioIBPUnidad;
/*  44:    */   private Producto producto;
/*  45:    */   private List<Producto> listaProducto;
/*  46:    */   private DataTable dtProducto;
/*  47:    */   private List<IBPClasificacion> listaIBPClasificacion;
/*  48:    */   private List<IBPCapacidad> listaIBPCapacidad;
/*  49:    */   private List<IBPUnidad> listaIBPUnidad;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 75 */     Map<String, String> filters = new HashMap();
/*  55: 76 */     filters.put("tipoProducto", TipoProducto.ARTICULO.toString());
/*  56: 77 */     filters.put("activo", "true");
/*  57: 78 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  58:    */     
/*  59: 80 */     List<String> listaCamposCargar = new ArrayList();
/*  60: 81 */     listaCamposCargar.add("subcategoriaProducto.categoriaProducto");
/*  61: 82 */     listaCamposCargar.add("ibpMarca.ibpClasificacion");
/*  62: 83 */     listaCamposCargar.add("ibpCapacidad");
/*  63: 84 */     listaCamposCargar.add("ibpUnidad");
/*  64:    */     
/*  65: 86 */     this.listaProducto = this.servicioProducto.obtenerListaPorPagina(Producto.class, 0, 100000, "nombre", true, filters, listaCamposCargar);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String editar()
/*  69:    */   {
/*  70: 92 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75: 98 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String eliminar()
/*  79:    */   {
/*  80:104 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String limpiar()
/*  84:    */   {
/*  85:110 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String cargarDatos()
/*  89:    */   {
/*  90:116 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Producto getProducto()
/*  94:    */   {
/*  95:120 */     return this.producto;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setProducto(Producto producto)
/*  99:    */   {
/* 100:124 */     this.producto = producto;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public DataTable getDtProducto()
/* 104:    */   {
/* 105:128 */     return this.dtProducto;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setDtProducto(DataTable dtProducto)
/* 109:    */   {
/* 110:132 */     this.dtProducto = dtProducto;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<IBPClasificacion> getListaIBPClasificacion()
/* 114:    */   {
/* 115:136 */     if (this.listaIBPClasificacion == null)
/* 116:    */     {
/* 117:137 */       Map<String, String> filters = new HashMap();
/* 118:138 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 119:139 */       filters.put("activo", "true");
/* 120:140 */       this.listaIBPClasificacion = this.servicioIBPClasificacion.obtenerListaCombo(IBPClasificacion.class, "nombre", true, filters);
/* 121:    */     }
/* 122:142 */     return this.listaIBPClasificacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<IBPCapacidad> getListaIBPCapacidad()
/* 126:    */   {
/* 127:146 */     if (this.listaIBPCapacidad == null)
/* 128:    */     {
/* 129:147 */       Map<String, String> filters = new HashMap();
/* 130:148 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 131:149 */       filters.put("activo", "true");
/* 132:150 */       this.listaIBPCapacidad = this.servicioIBPCapacidad.obtenerListaCombo(IBPCapacidad.class, "nombre", true, filters);
/* 133:    */     }
/* 134:152 */     return this.listaIBPCapacidad;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<IBPUnidad> getListaIBPUnidad()
/* 138:    */   {
/* 139:156 */     if (this.listaIBPUnidad == null)
/* 140:    */     {
/* 141:157 */       Map<String, String> filters = new HashMap();
/* 142:158 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 143:159 */       filters.put("activo", "true");
/* 144:160 */       this.listaIBPUnidad = this.servicioIBPUnidad.obtenerListaCombo(IBPUnidad.class, "nombre", true, filters);
/* 145:    */     }
/* 146:162 */     return this.listaIBPUnidad;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void actualizarIBPClasificacion(Producto producto)
/* 150:    */   {
/* 151:166 */     producto.setIbpMarca(null);
/* 152:167 */     List<IBPMarca> listaIBPMarca = new ArrayList();
/* 153:168 */     if (producto.getIbpClasificacion() != null)
/* 154:    */     {
/* 155:169 */       Map<String, String> filters = new HashMap();
/* 156:170 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 157:171 */       filters.put("activo", "true");
/* 158:172 */       filters.put("activo", "true");
/* 159:173 */       filters.put("ibpClasificacion.idIBPClasificacion", producto.getIbpClasificacion().getId() + "");
/* 160:174 */       listaIBPMarca = this.servicioIBPMarca.obtenerListaCombo(IBPMarca.class, "nombre", true, filters);
/* 161:    */     }
/* 162:176 */     producto.setListaIBPMarca(listaIBPMarca);
/* 163:177 */     guardar(producto);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void guardar(Producto producto)
/* 167:    */   {
/* 168:    */     try
/* 169:    */     {
/* 170:182 */       this.servicioProducto.guardar(producto);
/* 171:183 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 172:    */     }
/* 173:    */     catch (AS2Exception e)
/* 174:    */     {
/* 175:185 */       JsfUtil.addErrorMessage(e, "");
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<Producto> getListaProducto()
/* 180:    */   {
/* 181:190 */     return this.listaProducto;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setListaProducto(List<Producto> listaProducto)
/* 185:    */   {
/* 186:194 */     this.listaProducto = listaProducto;
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.IBPProductoBean
 * JD-Core Version:    0.7.0.1
 */