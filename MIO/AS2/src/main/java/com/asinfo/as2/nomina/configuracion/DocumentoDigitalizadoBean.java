/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
/*   6:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCategoriaDocumentoDigitalizado;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDocumentoDigitalizado;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import javax.faces.model.SelectItem;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class DocumentoDigitalizadoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 5562328087712294452L;
/*  32:    */   @EJB
/*  33:    */   private ServicioDocumentoDigitalizado servicioDocumentoDigitalizado;
/*  34:    */   @EJB
/*  35:    */   private ServicioCategoriaDocumentoDigitalizado servicioCategoriaDocumentoDigitalizado;
/*  36:    */   private DocumentoDigitalizado documentoDigitalizado;
/*  37:    */   private LazyDataModel<DocumentoDigitalizado> listaDocumentoDigitalizado;
/*  38:    */   private DataTable dtDocumentoDigitalizado;
/*  39:    */   private List<SelectItem> listaCategorias;
/*  40:    */   private List<SelectItem> listaCategoriasFiltro;
/*  41:    */   
/*  42:    */   @PostConstruct
/*  43:    */   public void init()
/*  44:    */   {
/*  45: 58 */     this.listaDocumentoDigitalizado = new LazyDataModel()
/*  46:    */     {
/*  47:    */       private static final long serialVersionUID = 1L;
/*  48:    */       
/*  49:    */       public List<DocumentoDigitalizado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  50:    */       {
/*  51: 68 */         List<DocumentoDigitalizado> lista = new ArrayList();
/*  52: 69 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  53: 70 */         filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/*  54: 71 */         lista = DocumentoDigitalizadoBean.this.servicioDocumentoDigitalizado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  55:    */         
/*  56: 73 */         DocumentoDigitalizadoBean.this.listaDocumentoDigitalizado.setRowCount(DocumentoDigitalizadoBean.this.servicioDocumentoDigitalizado.contarPorCriterio(filters));
/*  57:    */         
/*  58: 75 */         return lista;
/*  59:    */       }
/*  60:    */     };
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65: 82 */     if ((getDocumentoDigitalizado() != null) && (getDocumentoDigitalizado().getIdDocumentoDigitalizado() != 0))
/*  66:    */     {
/*  67: 83 */       this.documentoDigitalizado = this.servicioDocumentoDigitalizado.cargarDetalles(this.documentoDigitalizado.getIdDocumentoDigitalizado());
/*  68: 84 */       setEditado(true);
/*  69:    */     }
/*  70:    */     else
/*  71:    */     {
/*  72: 86 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  73:    */     }
/*  74: 88 */     return "";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String guardar()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81: 94 */       this.documentoDigitalizado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  82: 95 */       this.documentoDigitalizado.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  83: 96 */       this.servicioDocumentoDigitalizado.guardar(this.documentoDigitalizado);
/*  84: 97 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  85: 98 */       setEditado(false);
/*  86: 99 */       limpiar();
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:101 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  91:102 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  92:    */     }
/*  93:104 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String eliminar()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:110 */       this.servicioDocumentoDigitalizado.eliminar(this.documentoDigitalizado);
/* 101:111 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:113 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 106:114 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 107:    */     }
/* 108:116 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String limpiar()
/* 112:    */   {
/* 113:121 */     this.documentoDigitalizado = new DocumentoDigitalizado();
/* 114:122 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String cargarDatos()
/* 118:    */   {
/* 119:127 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public DocumentoDigitalizado getDocumentoDigitalizado()
/* 123:    */   {
/* 124:131 */     if (this.documentoDigitalizado == null) {
/* 125:132 */       this.documentoDigitalizado = new DocumentoDigitalizado();
/* 126:    */     }
/* 127:134 */     return this.documentoDigitalizado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setDocumentoDigitalizado(DocumentoDigitalizado documentoDigitalizado)
/* 131:    */   {
/* 132:138 */     this.documentoDigitalizado = documentoDigitalizado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public LazyDataModel<DocumentoDigitalizado> getListaDocumentoDigitalizado()
/* 136:    */   {
/* 137:142 */     return this.listaDocumentoDigitalizado;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setListaDocumentoDigitalizado(LazyDataModel<DocumentoDigitalizado> listaDocumentoDigitalizado)
/* 141:    */   {
/* 142:147 */     this.listaDocumentoDigitalizado = listaDocumentoDigitalizado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public DataTable getDtDocumentoDigitalizado()
/* 146:    */   {
/* 147:151 */     return this.dtDocumentoDigitalizado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setDtDocumentoDigitalizado(DataTable dtDocumentoDigitalizado)
/* 151:    */   {
/* 152:155 */     this.dtDocumentoDigitalizado = dtDocumentoDigitalizado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<SelectItem> getListaCategorias()
/* 156:    */   {
/* 157:159 */     if (this.listaCategorias == null)
/* 158:    */     {
/* 159:160 */       this.listaCategorias = new ArrayList();
/* 160:161 */       Map<String, String> filters = new HashMap();
/* 161:162 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 162:163 */       filters.put("activo", "true");
/* 163:164 */       List<CategoriaDocumentoDigitalizado> lista = this.servicioCategoriaDocumentoDigitalizado.obtenerListaCombo("nombre", true, filters);
/* 164:165 */       for (CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado : lista) {
/* 165:166 */         this.listaCategorias.add(new SelectItem(categoriaDocumentoDigitalizado, categoriaDocumentoDigitalizado.getNombre()));
/* 166:    */       }
/* 167:    */     }
/* 168:169 */     return this.listaCategorias;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setListaCategorias(List<SelectItem> listaCategorias)
/* 172:    */   {
/* 173:173 */     this.listaCategorias = listaCategorias;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<SelectItem> getListaCategoriasFiltro()
/* 177:    */   {
/* 178:177 */     if (this.listaCategoriasFiltro == null)
/* 179:    */     {
/* 180:178 */       this.listaCategoriasFiltro = new ArrayList();
/* 181:179 */       this.listaCategoriasFiltro.add(new SelectItem("%%", ""));
/* 182:    */       
/* 183:181 */       Map<String, String> filters = new HashMap();
/* 184:182 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 185:183 */       List<CategoriaDocumentoDigitalizado> lista = this.servicioCategoriaDocumentoDigitalizado.obtenerListaCombo("nombre", true, filters);
/* 186:184 */       for (CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado : lista) {
/* 187:185 */         this.listaCategoriasFiltro.add(new SelectItem(categoriaDocumentoDigitalizado.getNombre(), categoriaDocumentoDigitalizado.getNombre()));
/* 188:    */       }
/* 189:    */     }
/* 190:188 */     return this.listaCategoriasFiltro;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setListaCategoriasFiltro(List<SelectItem> listaCategoriasFiltro)
/* 194:    */   {
/* 195:192 */     this.listaCategoriasFiltro = listaCategoriasFiltro;
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.DocumentoDigitalizadoBean
 * JD-Core Version:    0.7.0.1
 */