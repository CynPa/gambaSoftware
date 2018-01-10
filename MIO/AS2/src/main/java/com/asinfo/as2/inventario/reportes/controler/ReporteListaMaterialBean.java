/*   1:    */ package com.asinfo.as2.inventario.reportes.controler;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.Unidad;
/*   8:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteInventarioProducto;
/*   9:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.lang.reflect.Array;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import net.sf.jasperreports.engine.JRException;
/*  22:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ReporteListaMaterialBean
/*  28:    */   extends AbstractBaseReportBean
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 770222109067650882L;
/*  31:    */   @EJB
/*  32:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  33:    */   @EJB
/*  34:    */   private ServicioReporteInventarioProducto servicioReporteInventarioProducto;
/*  35:    */   private Producto producto;
/*  36:    */   
/*  37:    */   public Producto getProducto()
/*  38:    */   {
/*  39: 53 */     if (this.producto == null) {
/*  40: 54 */       this.producto = new Producto();
/*  41:    */     }
/*  42: 56 */     return this.producto;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setProducto(Producto producto)
/*  46:    */   {
/*  47: 60 */     this.producto = producto;
/*  48:    */   }
/*  49:    */   
/*  50: 63 */   private final String COMPILE_FILE_NAME = "reporteListaMaterial";
/*  51:    */   
/*  52:    */   protected JRDataSource getJRDataSource()
/*  53:    */   {
/*  54: 68 */     List listaDatosReporteMateriales = new ArrayList();
/*  55: 69 */     List listaDatosReporteSubproductos = new ArrayList();
/*  56:    */     
/*  57:    */ 
/*  58: 72 */     JRDataSource ds = null;
/*  59:    */     try
/*  60:    */     {
/*  61: 74 */       List<ReporteListaMateriales> listaReporteListaMateriales = new ArrayList();
/*  62: 75 */       ReporteListaMateriales reporteListaMateriales = new ReporteListaMateriales();
/*  63: 76 */       List<ClaseReporteMaterial> listaClaseReporteMaterial = new ArrayList();
/*  64: 77 */       List<ClaseReporteSubproductos> listaClaseReporteSubproductos = new ArrayList();
/*  65:    */       
/*  66:    */ 
/*  67: 80 */       listaDatosReporteMateriales = this.servicioReporteInventarioProducto.getReporteListaMaterial(getProducto().getId());
/*  68: 81 */       listaDatosReporteSubproductos = this.servicioReporteInventarioProducto.getReporteListaMaterialSubproductos(getProducto().getId());
/*  69: 83 */       for (int i = 0; i < listaDatosReporteMateriales.size(); i++)
/*  70:    */       {
/*  71: 84 */         ClaseReporteMaterial claseReporteMaterial = new ClaseReporteMaterial();
/*  72: 85 */         claseReporteMaterial.setF_codigo(Array.get(listaDatosReporteMateriales.get(i), 0).toString());
/*  73: 86 */         claseReporteMaterial.setF_nombre(Array.get(listaDatosReporteMateriales.get(i), 1).toString());
/*  74: 87 */         claseReporteMaterial.setF_unidad(Array.get(listaDatosReporteMateriales.get(i), 2).toString());
/*  75: 88 */         claseReporteMaterial.setF_cantidad(new BigDecimal(Array.get(listaDatosReporteMateriales.get(i), 3).toString()));
/*  76: 89 */         claseReporteMaterial.setF_nota(Array.get(listaDatosReporteMateriales.get(i), 4).toString());
/*  77: 90 */         listaClaseReporteMaterial.add(claseReporteMaterial);
/*  78:    */       }
/*  79: 93 */       for (int i = 0; i < listaDatosReporteSubproductos.size(); i++)
/*  80:    */       {
/*  81: 94 */         ClaseReporteSubproductos claseReporteSubproductos = new ClaseReporteSubproductos();
/*  82: 95 */         claseReporteSubproductos.setF_codigo(Array.get(listaDatosReporteSubproductos.get(i), 0).toString());
/*  83: 96 */         claseReporteSubproductos.setF_nombre(Array.get(listaDatosReporteSubproductos.get(i), 1).toString());
/*  84: 97 */         claseReporteSubproductos.setF_xcientoCosto(new BigDecimal(Array.get(listaDatosReporteSubproductos.get(i), 2).toString()));
/*  85: 98 */         claseReporteSubproductos.setF_xcientoCantidad(new BigDecimal(Array.get(listaDatosReporteSubproductos.get(i), 3).toString()));
/*  86: 99 */         claseReporteSubproductos.setF_cantidad(new BigDecimal(Array.get(listaDatosReporteSubproductos.get(i), 4).toString()));
/*  87:100 */         if (Array.get(listaDatosReporteSubproductos.get(i), 5) == null) {
/*  88:101 */           claseReporteSubproductos.setF_nota("");
/*  89:    */         } else {
/*  90:103 */           claseReporteSubproductos.setF_nota(Array.get(listaDatosReporteSubproductos.get(i), 5).toString());
/*  91:    */         }
/*  92:105 */         listaClaseReporteSubproductos.add(claseReporteSubproductos);
/*  93:    */       }
/*  94:108 */       reporteListaMateriales.setListaMateriales(listaClaseReporteMaterial);
/*  95:109 */       reporteListaMateriales.setListaSubproductos(listaClaseReporteSubproductos);
/*  96:    */       
/*  97:111 */       listaReporteListaMateriales.add(reporteListaMateriales);
/*  98:112 */       ds = new JRBeanCollectionDataSource(listaReporteListaMateriales);
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:114 */       e.printStackTrace();
/* 103:    */     }
/* 104:116 */     return ds;
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected String getCompileFileName()
/* 108:    */   {
/* 109:121 */     return "reporteListaMaterial";
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected Map<String, Object> getReportParameters()
/* 113:    */   {
/* 114:127 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 115:128 */     reportParameters.put("ReportTitle", "Producto");
/* 116:129 */     reportParameters.put("p_codigo", getProducto().getCodigo());
/* 117:130 */     reportParameters.put("p_nombre", getProducto().getNombre());
/* 118:131 */     reportParameters.put("p_unidad", getProducto().getUnidad().getNombre());
/* 119:132 */     reportParameters.put("p_categoria", getProducto().getSubcategoriaProducto().getCategoriaProducto().getNombre());
/* 120:133 */     reportParameters.put("p_subcatergoria", getProducto().getSubcategoriaProducto().getNombre());
/* 121:134 */     reportParameters.put("p_cantidadProduccion", getProducto().getCantidadProduccion());
/* 122:135 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/* 123:    */     
/* 124:137 */     return reportParameters;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String execute()
/* 128:    */   {
/* 129:    */     try
/* 130:    */     {
/* 131:146 */       super.prepareReport();
/* 132:    */     }
/* 133:    */     catch (JRException e)
/* 134:    */     {
/* 135:149 */       LOG.info("Error JRException");
/* 136:150 */       e.printStackTrace();
/* 137:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 138:    */     }
/* 139:    */     catch (IOException e)
/* 140:    */     {
/* 141:153 */       LOG.info("Error IOException");
/* 142:154 */       e.printStackTrace();
/* 143:155 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 144:    */     }
/* 145:158 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public class ReporteListaMateriales
/* 149:    */   {
/* 150:    */     List<ReporteListaMaterialBean.ClaseReporteMaterial> listaMateriales;
/* 151:    */     List<ReporteListaMaterialBean.ClaseReporteSubproductos> listaSubproductos;
/* 152:    */     
/* 153:    */     public ReporteListaMateriales() {}
/* 154:    */     
/* 155:    */     public List<ReporteListaMaterialBean.ClaseReporteMaterial> getListaMateriales()
/* 156:    */     {
/* 157:165 */       return this.listaMateriales;
/* 158:    */     }
/* 159:    */     
/* 160:    */     public void setListaMateriales(List<ReporteListaMaterialBean.ClaseReporteMaterial> listaMateriales)
/* 161:    */     {
/* 162:168 */       this.listaMateriales = listaMateriales;
/* 163:    */     }
/* 164:    */     
/* 165:    */     public List<ReporteListaMaterialBean.ClaseReporteSubproductos> getListaSubproductos()
/* 166:    */     {
/* 167:171 */       return this.listaSubproductos;
/* 168:    */     }
/* 169:    */     
/* 170:    */     public void setListaSubproductos(List<ReporteListaMaterialBean.ClaseReporteSubproductos> listaSubproductos)
/* 171:    */     {
/* 172:174 */       this.listaSubproductos = listaSubproductos;
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public class ClaseReporteMaterial
/* 177:    */   {
/* 178:    */     private String f_codigo;
/* 179:    */     private String f_nombre;
/* 180:    */     private String f_unidad;
/* 181:    */     private String f_nota;
/* 182:    */     private BigDecimal f_cantidad;
/* 183:    */     
/* 184:    */     public ClaseReporteMaterial() {}
/* 185:    */     
/* 186:    */     public BigDecimal getF_cantidad()
/* 187:    */     {
/* 188:186 */       return this.f_cantidad;
/* 189:    */     }
/* 190:    */     
/* 191:    */     public void setF_cantidad(BigDecimal f_cantidad)
/* 192:    */     {
/* 193:189 */       this.f_cantidad = f_cantidad;
/* 194:    */     }
/* 195:    */     
/* 196:    */     public String getF_codigo()
/* 197:    */     {
/* 198:192 */       return this.f_codigo;
/* 199:    */     }
/* 200:    */     
/* 201:    */     public void setF_codigo(String f_codigo)
/* 202:    */     {
/* 203:195 */       this.f_codigo = f_codigo;
/* 204:    */     }
/* 205:    */     
/* 206:    */     public String getF_nombre()
/* 207:    */     {
/* 208:198 */       return this.f_nombre;
/* 209:    */     }
/* 210:    */     
/* 211:    */     public void setF_nombre(String f_nombre)
/* 212:    */     {
/* 213:201 */       this.f_nombre = f_nombre;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public String getF_unidad()
/* 217:    */     {
/* 218:204 */       return this.f_unidad;
/* 219:    */     }
/* 220:    */     
/* 221:    */     public void setF_unidad(String f_unidad)
/* 222:    */     {
/* 223:207 */       this.f_unidad = f_unidad;
/* 224:    */     }
/* 225:    */     
/* 226:    */     public String getF_nota()
/* 227:    */     {
/* 228:210 */       return this.f_nota;
/* 229:    */     }
/* 230:    */     
/* 231:    */     public void setF_nota(String f_nota)
/* 232:    */     {
/* 233:213 */       this.f_nota = f_nota;
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public class ClaseReporteSubproductos
/* 238:    */   {
/* 239:    */     private String f_codigo;
/* 240:    */     private String f_nombre;
/* 241:    */     private BigDecimal f_xcientoCosto;
/* 242:    */     private BigDecimal f_xcientoCantidad;
/* 243:    */     private String f_nota;
/* 244:    */     private BigDecimal f_cantidad;
/* 245:    */     
/* 246:    */     public ClaseReporteSubproductos() {}
/* 247:    */     
/* 248:    */     public BigDecimal getF_cantidad()
/* 249:    */     {
/* 250:227 */       return this.f_cantidad;
/* 251:    */     }
/* 252:    */     
/* 253:    */     public void setF_cantidad(BigDecimal f_cantidad)
/* 254:    */     {
/* 255:230 */       this.f_cantidad = f_cantidad;
/* 256:    */     }
/* 257:    */     
/* 258:    */     public String getF_codigo()
/* 259:    */     {
/* 260:233 */       return this.f_codigo;
/* 261:    */     }
/* 262:    */     
/* 263:    */     public void setF_codigo(String f_codigo)
/* 264:    */     {
/* 265:236 */       this.f_codigo = f_codigo;
/* 266:    */     }
/* 267:    */     
/* 268:    */     public String getF_nombre()
/* 269:    */     {
/* 270:239 */       return this.f_nombre;
/* 271:    */     }
/* 272:    */     
/* 273:    */     public void setF_nombre(String f_nombre)
/* 274:    */     {
/* 275:242 */       this.f_nombre = f_nombre;
/* 276:    */     }
/* 277:    */     
/* 278:    */     public String getF_nota()
/* 279:    */     {
/* 280:245 */       return this.f_nota;
/* 281:    */     }
/* 282:    */     
/* 283:    */     public void setF_nota(String f_nota)
/* 284:    */     {
/* 285:248 */       this.f_nota = f_nota;
/* 286:    */     }
/* 287:    */     
/* 288:    */     public BigDecimal getF_xcientoCosto()
/* 289:    */     {
/* 290:251 */       return this.f_xcientoCosto;
/* 291:    */     }
/* 292:    */     
/* 293:    */     public void setF_xcientoCosto(BigDecimal f_xcientoCosto)
/* 294:    */     {
/* 295:254 */       this.f_xcientoCosto = f_xcientoCosto;
/* 296:    */     }
/* 297:    */     
/* 298:    */     public BigDecimal getF_xcientoCantidad()
/* 299:    */     {
/* 300:257 */       return this.f_xcientoCantidad;
/* 301:    */     }
/* 302:    */     
/* 303:    */     public void setF_xcientoCantidad(BigDecimal f_xcientoCantidad)
/* 304:    */     {
/* 305:260 */       this.f_xcientoCantidad = f_xcientoCantidad;
/* 306:    */     }
/* 307:    */   }
/* 308:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controler.ReporteListaMaterialBean
 * JD-Core Version:    0.7.0.1
 */