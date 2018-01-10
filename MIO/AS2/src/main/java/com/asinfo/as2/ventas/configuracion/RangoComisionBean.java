/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Canal;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.RangoComision;
/*   9:    */ import com.asinfo.as2.entities.RangoComisionCategoriaProducto;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  14:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRangoComision;
/*  15:    */ import java.math.BigDecimal;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.model.LazyDataModel;
/*  26:    */ import org.primefaces.model.SortOrder;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class RangoComisionBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private ServicioRangoComision servicioRangoComision;
/*  36:    */   @EJB
/*  37:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  38:    */   @EJB
/*  39:    */   private ServicioCanal servicioCanal;
/*  40:    */   private RangoComision rangoComision;
/*  41:    */   private LazyDataModel<RangoComision> listaRangoComision;
/*  42:    */   private List<CategoriaProducto> listaCategoriaProducto;
/*  43:    */   private List<Canal> listaCanal;
/*  44:    */   private DataTable dtRangoComision;
/*  45:    */   private DataTable dtRangoComisionCategoriaProducto;
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 72 */     this.listaRangoComision = new LazyDataModel()
/*  51:    */     {
/*  52:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  53:    */       
/*  54:    */       public List<RangoComision> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  55:    */       {
/*  56: 79 */         List<RangoComision> lista = new ArrayList();
/*  57: 80 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  58:    */         
/*  59: 82 */         lista = RangoComisionBean.this.servicioRangoComision.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  60:    */         
/*  61: 84 */         RangoComisionBean.this.listaRangoComision.setRowCount(RangoComisionBean.this.servicioRangoComision.contarPorCriterio(filters));
/*  62: 85 */         return lista;
/*  63:    */       }
/*  64:    */     };
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String editar()
/*  68:    */   {
/*  69: 98 */     if (getRangoComision().getId() > 0)
/*  70:    */     {
/*  71: 99 */       this.rangoComision = this.servicioRangoComision.cargarDetalle(getRangoComision().getId());
/*  72:100 */       setEditado(true);
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76:102 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  77:    */     }
/*  78:105 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String guardar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:117 */       int result = this.rangoComision.getValorDesde().compareTo(this.rangoComision.getValorHasta());
/*  86:118 */       if ((result == 0) || (result == -1))
/*  87:    */       {
/*  88:119 */         this.servicioRangoComision.guardar(this.rangoComision);
/*  89:120 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  90:121 */         limpiar();
/*  91:    */       }
/*  92:    */       else
/*  93:    */       {
/*  94:123 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  95:    */       }
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:127 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 100:    */     }
/* 101:129 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String eliminar()
/* 105:    */   {
/* 106:140 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void eliminarDetalle(RangoComisionCategoriaProducto rangoComision)
/* 110:    */   {
/* 111:144 */     rangoComision.setEliminado(true);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String limpiar()
/* 115:    */   {
/* 116:155 */     setEditado(false);
/* 117:    */     
/* 118:157 */     this.rangoComision = new RangoComision();
/* 119:158 */     this.rangoComision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 120:159 */     this.rangoComision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 121:160 */     this.rangoComision.setIdSucursal(AppUtil.getSucursal().getId());
/* 122:    */     
/* 123:162 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String cargarDatos()
/* 127:    */   {
/* 128:172 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String agregarDetalle()
/* 132:    */   {
/* 133:182 */     RangoComisionCategoriaProducto rccp = new RangoComisionCategoriaProducto();
/* 134:183 */     rccp.setRangoComision(this.rangoComision);
/* 135:184 */     rccp.setCategoriaProducto(new CategoriaProducto());
/* 136:185 */     rccp.setCanal(new Canal());
/* 137:186 */     rccp.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 138:    */     
/* 139:188 */     this.rangoComision.getListaRangoComisionCategoriaProducto().add(rccp);
/* 140:    */     
/* 141:190 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<RangoComisionCategoriaProducto> getListaRangoComisionCategoriaProducto()
/* 145:    */   {
/* 146:198 */     List<RangoComisionCategoriaProducto> lista = new ArrayList();
/* 147:200 */     for (RangoComisionCategoriaProducto rccp : this.rangoComision.getListaRangoComisionCategoriaProducto()) {
/* 148:201 */       if (!rccp.isEliminado()) {
/* 149:202 */         lista.add(rccp);
/* 150:    */       }
/* 151:    */     }
/* 152:206 */     return lista;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public RangoComision getRangoComision()
/* 156:    */   {
/* 157:215 */     return this.rangoComision;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setRangoComision(RangoComision rangoComision)
/* 161:    */   {
/* 162:225 */     this.rangoComision = rangoComision;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public LazyDataModel<RangoComision> getListaRangoComision()
/* 166:    */   {
/* 167:234 */     return this.listaRangoComision;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaRangoComision(LazyDataModel<RangoComision> listaRangoComision)
/* 171:    */   {
/* 172:244 */     this.listaRangoComision = listaRangoComision;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public DataTable getDtRangoComision()
/* 176:    */   {
/* 177:253 */     return this.dtRangoComision;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setDtRangoComision(DataTable dtRangoComision)
/* 181:    */   {
/* 182:263 */     this.dtRangoComision = dtRangoComision;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public DataTable getDtRangoComisionCategoriaProducto()
/* 186:    */   {
/* 187:267 */     return this.dtRangoComisionCategoriaProducto;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setDtRangoComisionCategoriaProducto(DataTable dtRangoComisionCategoriaProducto)
/* 191:    */   {
/* 192:271 */     this.dtRangoComisionCategoriaProducto = dtRangoComisionCategoriaProducto;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public List<CategoriaProducto> getListaCategoriaProducto()
/* 196:    */   {
/* 197:275 */     if (this.listaCategoriaProducto == null) {
/* 198:276 */       this.listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 199:    */     }
/* 200:278 */     return this.listaCategoriaProducto;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setListaCategoriaProducto(List<CategoriaProducto> listaCategoriaProducto)
/* 204:    */   {
/* 205:282 */     this.listaCategoriaProducto = listaCategoriaProducto;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<Canal> getListaCanal()
/* 209:    */   {
/* 210:286 */     if (this.listaCanal == null) {
/* 211:287 */       this.listaCanal = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 212:    */     }
/* 213:289 */     return this.listaCanal;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setListaCanal(List<Canal> listaCanal)
/* 217:    */   {
/* 218:293 */     this.listaCanal = listaCanal;
/* 219:    */   }
/* 220:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.RangoComisionBean
 * JD-Core Version:    0.7.0.1
 */