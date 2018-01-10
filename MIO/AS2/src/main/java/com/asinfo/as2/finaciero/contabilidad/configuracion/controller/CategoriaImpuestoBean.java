/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   6:    */ import com.asinfo.as2.entities.Impuesto;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
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
/*  27:    */ public class CategoriaImpuestoBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 4751571124967962033L;
/*  31:    */   @EJB
/*  32:    */   private ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  33:    */   @EJB
/*  34:    */   private ServicioImpuesto servicioImpuesto;
/*  35:    */   private CategoriaImpuesto categoriaImpuesto;
/*  36:    */   private Impuesto[] impuestosSeleccionados;
/*  37:    */   private DataTable dtCategoriaImpuesto;
/*  38:    */   private DataTable dtImpuesto;
/*  39:    */   private LazyDataModel<CategoriaImpuesto> listaCategoriaImpuesto;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 67 */     this.listaCategoriaImpuesto = new LazyDataModel()
/*  45:    */     {
/*  46:    */       private static final long serialVersionUID = 1L;
/*  47:    */       
/*  48:    */       public List<CategoriaImpuesto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 73 */         List<CategoriaImpuesto> lista = new ArrayList();
/*  51:    */         
/*  52: 75 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  53: 77 */         if (sortField == null)
/*  54:    */         {
/*  55: 78 */           sortField = "codigo";
/*  56: 79 */           ordenar = true;
/*  57:    */         }
/*  58: 82 */         lista = CategoriaImpuestoBean.this.servicioCategoriaImpuesto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  59:    */         
/*  60: 84 */         CategoriaImpuestoBean.this.listaCategoriaImpuesto.setRowCount(CategoriaImpuestoBean.this.servicioCategoriaImpuesto.contarPorCriterio(filters));
/*  61:    */         
/*  62: 86 */         return lista;
/*  63:    */       }
/*  64:    */     };
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String editar()
/*  68:    */   {
/*  69: 98 */     if (getCategoriaImpuesto().getId() > 0) {
/*  70:    */       try
/*  71:    */       {
/*  72:100 */         this.categoriaImpuesto = this.servicioCategoriaImpuesto.cargarDetalle(this.categoriaImpuesto.getId());
/*  73:101 */         setEditado(true);
/*  74:    */       }
/*  75:    */       catch (Exception e)
/*  76:    */       {
/*  77:104 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  78:105 */         LOG.error("Error al editar pedido de cliente", e);
/*  79:    */       }
/*  80:    */     } else {
/*  81:108 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  82:    */     }
/*  83:110 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String guardar()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:121 */       this.servicioCategoriaImpuesto.guardar(this.categoriaImpuesto);
/*  91:122 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  92:123 */       limpiar();
/*  93:124 */       setEditado(false);
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:126 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  98:127 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  99:    */     }
/* 100:129 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String eliminar()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:140 */       this.servicioCategoriaImpuesto.eliminar(this.categoriaImpuesto);
/* 108:141 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 109:    */     }
/* 110:    */     catch (Exception e)
/* 111:    */     {
/* 112:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 113:    */     }
/* 114:145 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String limpiar()
/* 118:    */   {
/* 119:155 */     crearCategoriaImpuesto();
/* 120:156 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String cargarDatos()
/* 124:    */   {
/* 125:166 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void crearCategoriaImpuesto()
/* 129:    */   {
/* 130:170 */     this.categoriaImpuesto = new CategoriaImpuesto();
/* 131:171 */     this.categoriaImpuesto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 132:172 */     this.categoriaImpuesto.setIdSucursal(AppUtil.getSucursal().getId());
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String eliminarDetalle()
/* 136:    */   {
/* 137:181 */     Impuesto impuesto = (Impuesto)this.dtImpuesto.getRowData();
/* 138:182 */     impuesto.setEliminado(true);
/* 139:183 */     this.categoriaImpuesto.getListaImpuesto().remove(impuesto);
/* 140:184 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<Impuesto> getListaImpuesto()
/* 144:    */   {
/* 145:188 */     List<Impuesto> lista = new ArrayList();
/* 146:189 */     for (Impuesto impuesto : getCategoriaImpuesto().getListaImpuesto()) {
/* 147:190 */       if (!impuesto.isEliminado()) {
/* 148:191 */         lista.add(impuesto);
/* 149:    */       }
/* 150:    */     }
/* 151:194 */     return lista;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<Impuesto> getListaImpuestosNoAsignados()
/* 155:    */   {
/* 156:198 */     List<Impuesto> listaImpuestosNoAsignados = new ArrayList();
/* 157:199 */     this.impuestosSeleccionados = null;
/* 158:200 */     HashMap<Integer, Impuesto> impuestos = new HashMap();
/* 159:201 */     for (Impuesto impuesto : this.servicioImpuesto.obtenerListaCombo("codigo", true, null)) {
/* 160:202 */       impuestos.put(Integer.valueOf(impuesto.getId()), impuesto);
/* 161:    */     }
/* 162:205 */     for (Impuesto impuesto : getCategoriaImpuesto().getListaImpuesto()) {
/* 163:206 */       impuestos.remove(Integer.valueOf(impuesto.getId()));
/* 164:    */     }
/* 165:209 */     listaImpuestosNoAsignados = new ArrayList(impuestos.values());
/* 166:210 */     return listaImpuestosNoAsignados;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String asignarImpuestos()
/* 170:    */   {
/* 171:219 */     if (this.impuestosSeleccionados != null) {
/* 172:220 */       for (Impuesto impuesto : this.impuestosSeleccionados) {
/* 173:221 */         getCategoriaImpuesto().getListaImpuesto().add(impuesto);
/* 174:    */       }
/* 175:    */     }
/* 176:224 */     return "";
/* 177:    */   }
/* 178:    */   
/* 179:    */   public CategoriaImpuesto getCategoriaImpuesto()
/* 180:    */   {
/* 181:235 */     if (this.categoriaImpuesto == null) {
/* 182:236 */       crearCategoriaImpuesto();
/* 183:    */     }
/* 184:238 */     return this.categoriaImpuesto;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setCategoriaImpuesto(CategoriaImpuesto categoriaImpuesto)
/* 188:    */   {
/* 189:248 */     this.categoriaImpuesto = categoriaImpuesto;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Impuesto[] getImpuestosSeleccionados()
/* 193:    */   {
/* 194:252 */     return this.impuestosSeleccionados;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setImpuestosSeleccionados(Impuesto[] impuestosSeleccionados)
/* 198:    */   {
/* 199:256 */     this.impuestosSeleccionados = impuestosSeleccionados;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public LazyDataModel<CategoriaImpuesto> getListaCategoriaImpuesto()
/* 203:    */   {
/* 204:260 */     return this.listaCategoriaImpuesto;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaCategoriaImpuesto(LazyDataModel<CategoriaImpuesto> listaCategoriaImpuesto)
/* 208:    */   {
/* 209:264 */     this.listaCategoriaImpuesto = listaCategoriaImpuesto;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public DataTable getDtCategoriaImpuesto()
/* 213:    */   {
/* 214:273 */     return this.dtCategoriaImpuesto;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setDtCategoriaImpuesto(DataTable dtCategoriaImpuesto)
/* 218:    */   {
/* 219:283 */     this.dtCategoriaImpuesto = dtCategoriaImpuesto;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public DataTable getDtImpuesto()
/* 223:    */   {
/* 224:287 */     return this.dtImpuesto;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setDtImpuesto(DataTable dtImpuesto)
/* 228:    */   {
/* 229:291 */     this.dtImpuesto = dtImpuesto;
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.CategoriaImpuestoBean
 * JD-Core Version:    0.7.0.1
 */