/*   1:    */ package com.asinfo.as2.finaciero.presupuesto.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
/*   8:    */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioNivelPartidaPresupuestaria;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import javax.faces.model.SelectItem;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTable;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class NivelPartidaPresupuestariaBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -9164828061077758215L;
/*  29:    */   @EJB
/*  30:    */   private ServicioNivelPartidaPresupuestaria servicioNivelPartidaPresupuestaria;
/*  31:    */   private NivelPartidaPresupuestaria nivelPartidaPresupuestaria;
/*  32:    */   public List<SelectItem> listaGrupoPartidaPresupuestaria;
/*  33:    */   private List<SelectItem> listaPartidaPresupuestariaPadre;
/*  34:    */   private LazyDataModel<NivelPartidaPresupuestaria> listaNivelPartidaPresupuestaria;
/*  35:    */   private DataTable dtNivelPartidaPresupuestaria;
/*  36:    */   
/*  37:    */   @PostConstruct
/*  38:    */   public void init()
/*  39:    */   {
/*  40: 75 */     this.listaNivelPartidaPresupuestaria = new LazyDataModel()
/*  41:    */     {
/*  42:    */       private static final long serialVersionUID = 1312949801168865877L;
/*  43:    */       
/*  44:    */       public List<NivelPartidaPresupuestaria> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  45:    */       {
/*  46: 85 */         List<NivelPartidaPresupuestaria> lista = new ArrayList();
/*  47:    */         
/*  48: 87 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  49:    */         
/*  50: 89 */         lista = NivelPartidaPresupuestariaBean.this.servicioNivelPartidaPresupuestaria.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  51:    */         
/*  52: 91 */         NivelPartidaPresupuestariaBean.this.listaNivelPartidaPresupuestaria.setRowCount(NivelPartidaPresupuestariaBean.this.servicioNivelPartidaPresupuestaria.contarPorCriterio(filters));
/*  53:    */         
/*  54: 93 */         return lista;
/*  55:    */       }
/*  56:    */     };
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void crearPartidaPresupuestaria()
/*  60:    */   {
/*  61:107 */     this.nivelPartidaPresupuestaria = new NivelPartidaPresupuestaria();
/*  62:108 */     this.nivelPartidaPresupuestaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  63:109 */     this.nivelPartidaPresupuestaria.setIdSucursal(AppUtil.getSucursal().getId());
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String editar()
/*  67:    */   {
/*  68:118 */     if (getNivelPartidaPresupuestaria().getIdNivelPartidaPresupuestaria() > 0)
/*  69:    */     {
/*  70:119 */       this.nivelPartidaPresupuestaria = this.servicioNivelPartidaPresupuestaria.cargarDetalle(this.nivelPartidaPresupuestaria.getId());
/*  71:120 */       setEditado(true);
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75:122 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  76:    */     }
/*  77:124 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String guardar()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:134 */       this.servicioNivelPartidaPresupuestaria.guardar(this.nivelPartidaPresupuestaria);
/*  85:135 */       limpiar();
/*  86:136 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  87:137 */       setEditado(false);
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  92:140 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  93:    */     }
/*  94:142 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String eliminar()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:152 */       this.servicioNivelPartidaPresupuestaria.eliminar(this.nivelPartidaPresupuestaria);
/* 102:153 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 103:154 */       limpiar();
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:156 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 108:157 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 109:    */     }
/* 110:159 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String cargarDatos()
/* 114:    */   {
/* 115:168 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String limpiar()
/* 119:    */   {
/* 120:177 */     crearPartidaPresupuestaria();
/* 121:178 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public NivelPartidaPresupuestaria getNivelPartidaPresupuestaria()
/* 125:    */   {
/* 126:191 */     return this.nivelPartidaPresupuestaria;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setNivelPartidaPresupuestaria(NivelPartidaPresupuestaria nivelPartidaPresupuestaria)
/* 130:    */   {
/* 131:201 */     this.nivelPartidaPresupuestaria = nivelPartidaPresupuestaria;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public List<SelectItem> getListaGrupoPartidaPresupuestaria()
/* 135:    */   {
/* 136:210 */     return this.listaGrupoPartidaPresupuestaria;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setListaGrupoPartidaPresupuestaria(List<SelectItem> listaGrupoPartidaPresupuestaria)
/* 140:    */   {
/* 141:220 */     this.listaGrupoPartidaPresupuestaria = listaGrupoPartidaPresupuestaria;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<SelectItem> getListaPartidaPresupuestariaPadre()
/* 145:    */   {
/* 146:229 */     return this.listaPartidaPresupuestariaPadre;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setListaPartidaPresupuestariaPadre(List<SelectItem> listaPartidaPresupuestariaPadre)
/* 150:    */   {
/* 151:239 */     this.listaPartidaPresupuestariaPadre = listaPartidaPresupuestariaPadre;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public LazyDataModel<NivelPartidaPresupuestaria> getListaNivelPartidaPresupuestaria()
/* 155:    */   {
/* 156:248 */     return this.listaNivelPartidaPresupuestaria;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setListaNivelPartidaPresupuestaria(LazyDataModel<NivelPartidaPresupuestaria> listaNivelPartidaPresupuestaria)
/* 160:    */   {
/* 161:258 */     this.listaNivelPartidaPresupuestaria = listaNivelPartidaPresupuestaria;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public DataTable getDtNivelPartidaPresupuestaria()
/* 165:    */   {
/* 166:267 */     return this.dtNivelPartidaPresupuestaria;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setDtNivelPartidaPresupuestaria(DataTable dtNivelPartidaPresupuestaria)
/* 170:    */   {
/* 171:277 */     this.dtNivelPartidaPresupuestaria = dtNivelPartidaPresupuestaria;
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.configuracion.NivelPartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */