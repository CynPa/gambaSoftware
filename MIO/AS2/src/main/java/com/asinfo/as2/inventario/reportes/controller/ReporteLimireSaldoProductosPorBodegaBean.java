/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Atributo;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*  14:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import com.asinfo.as2.ventas.reportes.AbstractInventarioReportBean;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class ReporteLimireSaldoProductosPorBodegaBean
/*  31:    */   extends AbstractInventarioReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34: 35 */   private final String COMPILE_FILE_NAME = "reporteSaldosMinimosProductosBodegas";
/*  35:    */   private List<CategoriaProducto> listaCategoriaProducto;
/*  36:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  37:    */   private List<Bodega> listaBodega;
/*  38:    */   private boolean indicadorBajoSaldoMinimo;
/*  39:    */   private CategoriaProducto categoriaProductoSelected;
/*  40:    */   private SubcategoriaProducto subcategoriaProductoSelected;
/*  41:    */   private Bodega bodegaSelected;
/*  42:    */   @EJB
/*  43:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  44:    */   @EJB
/*  45:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  46:    */   @EJB
/*  47:    */   private ServicioBodega servicioBodega;
/*  48:    */   @EJB
/*  49:    */   private ServicioProducto servicioProducto;
/*  50:    */   @EJB
/*  51:    */   private ServicioAtributo servicioAtributo;
/*  52:    */   @EJB
/*  53:    */   private ServicioValorAtributo servicioValorAtributo;
/*  54:    */   
/*  55:    */   protected JRDataSource getJRDataSource()
/*  56:    */   {
/*  57: 72 */     asignarValorAtributo(getValorAtributoSeleccionado());
/*  58: 73 */     List listaDatosReporte = new ArrayList();
/*  59: 74 */     JRDataSource ds = null;
/*  60: 75 */     String[] fields = null;
/*  61:    */     try
/*  62:    */     {
/*  63: 77 */       int idCategoria = this.categoriaProductoSelected == null ? 0 : this.categoriaProductoSelected.getId();
/*  64: 78 */       int idSubcategoria = this.subcategoriaProductoSelected == null ? 0 : this.subcategoriaProductoSelected.getId();
/*  65: 80 */       if (this.bodegaSelected != null) {
/*  66: 81 */         this.listaBodega = null;
/*  67:    */       }
/*  68: 84 */       listaDatosReporte = this.servicioProducto.getReporteSaldosMinimos(AppUtil.getOrganizacion().getId(), idCategoria, idSubcategoria, this.bodegaSelected, this.listaBodega, this.indicadorBajoSaldoMinimo, 
/*  69: 85 */         getValorAtributo());
/*  70:    */       
/*  71: 87 */       fields = new String[] { "f_codigo", "f_nombre", "f_subcategoria", "f_bodega", "f_saldo", "f_saldoMinimo", "f_saldoMaximo" };
/*  72:    */       
/*  73: 89 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77: 92 */       LOG.info("Error " + e);
/*  78: 93 */       e.printStackTrace();
/*  79:    */     }
/*  80: 95 */     return ds;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected String getCompileFileName()
/*  84:    */   {
/*  85:105 */     return "reporteSaldosMinimosProductosBodegas";
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected Map<String, Object> getReportParameters()
/*  89:    */   {
/*  90:111 */     asignarValorAtributo(getValorAtributoSeleccionado());
/*  91:    */     
/*  92:113 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  93:114 */     reportParameters.put("ReportTitle", "Reporte de Stock Mínimo de Productos por Bodega");
/*  94:115 */     reportParameters.put("p_categoria", this.categoriaProductoSelected != null ? this.categoriaProductoSelected.getNombre() : "Todos");
/*  95:116 */     reportParameters.put("p_bodega", this.bodegaSelected != null ? this.bodegaSelected.getNombre() : "Todos");
/*  96:117 */     reportParameters.put("p_subCategoriaProducto", this.subcategoriaProductoSelected != null ? this.subcategoriaProductoSelected.getNombre() : "Todos");
/*  97:118 */     reportParameters.put("p_atributo", getAtributo() != null ? getAtributo().getNombre() : "Todos");
/*  98:119 */     reportParameters.put("p_valorAtributo", (getValorAtributo() != null) && (getValorAtributo().isEmpty()) ? getValorAtributo() : "Todos");
/*  99:    */     
/* 100:121 */     return reportParameters;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String execute()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:131 */       super.prepareReport();
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:133 */       e.printStackTrace();
/* 112:    */     }
/* 113:136 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getCOMPILE_FILE_NAME()
/* 117:    */   {
/* 118:145 */     return "reporteSaldosMinimosProductosBodegas";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<CategoriaProducto> getListaCategoriaProducto()
/* 122:    */   {
/* 123:149 */     Map<String, String> filters = new HashMap();
/* 124:150 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 125:151 */     this.listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 126:152 */     return this.listaCategoriaProducto;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setListaCategoriaProducto(List<CategoriaProducto> listaCategoriaProducto)
/* 130:    */   {
/* 131:156 */     this.listaCategoriaProducto = listaCategoriaProducto;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 135:    */   {
/* 136:160 */     if (this.listaSubcategoriaProducto == null) {
/* 137:161 */       this.listaSubcategoriaProducto = new ArrayList();
/* 138:    */     }
/* 139:163 */     if (this.categoriaProductoSelected != null)
/* 140:    */     {
/* 141:164 */       Map<String, String> filters = new HashMap();
/* 142:165 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 143:166 */       filters.put("categoriaProducto.idCategoriaProducto", String.valueOf(this.categoriaProductoSelected.getId()));
/* 144:167 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 145:    */     }
/* 146:169 */     return this.listaSubcategoriaProducto;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 150:    */   {
/* 151:173 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isIndicadorBajoSaldoMinimo()
/* 155:    */   {
/* 156:177 */     return this.indicadorBajoSaldoMinimo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setIndicadorBajoSaldoMinimo(boolean indicadorBajoSaldoMinimo)
/* 160:    */   {
/* 161:181 */     this.indicadorBajoSaldoMinimo = indicadorBajoSaldoMinimo;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public CategoriaProducto getCategoriaProductoSelected()
/* 165:    */   {
/* 166:185 */     return this.categoriaProductoSelected;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setCategoriaProductoSelected(CategoriaProducto categoriaProductoSelected)
/* 170:    */   {
/* 171:189 */     this.categoriaProductoSelected = categoriaProductoSelected;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public SubcategoriaProducto getSubcategoriaProductoSelected()
/* 175:    */   {
/* 176:193 */     return this.subcategoriaProductoSelected;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setSubcategoriaProductoSelected(SubcategoriaProducto subcategoriaProductoSelected)
/* 180:    */   {
/* 181:197 */     this.subcategoriaProductoSelected = subcategoriaProductoSelected;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<Bodega> getListaBodega()
/* 185:    */   {
/* 186:201 */     if (this.listaBodega == null) {
/* 187:202 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 188:    */     }
/* 189:204 */     return this.listaBodega;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 193:    */   {
/* 194:208 */     this.listaBodega = listaBodega;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Bodega getBodegaSelected()
/* 198:    */   {
/* 199:212 */     return this.bodegaSelected;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setBodegaSelected(Bodega bodegaSelected)
/* 203:    */   {
/* 204:216 */     this.bodegaSelected = bodegaSelected;
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteLimireSaldoProductosPorBodegaBean
 * JD-Core Version:    0.7.0.1
 */