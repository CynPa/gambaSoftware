/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Canal;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class CanalBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioCanal servicioCanal;
/*  30:    */   private Canal canal;
/*  31:    */   private LazyDataModel<Canal> listaCanal;
/*  32:    */   private DataTable dtCanal;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 71 */     this.listaCanal = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<Canal> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 78 */         List<Canal> lista = new ArrayList();
/*  44: 79 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 81 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 82 */         lista = CanalBean.this.servicioCanal.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 84 */         CanalBean.this.listaCanal.setRowCount(CanalBean.this.servicioCanal.contarPorCriterio(filters));
/*  50:    */         
/*  51: 86 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearCanal()
/*  57:    */   {
/*  58:100 */     this.canal = new Canal();
/*  59:101 */     this.canal.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:102 */     this.canal.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:111 */     if (getCanal().getIdCanal() > 0) {
/*  66:112 */       setEditado(true);
/*  67:    */     } else {
/*  68:114 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:116 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:126 */       this.servicioCanal.guardar(this.canal);
/*  78:127 */       setEditado(false);
/*  79:128 */       limpiar();
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  84:131 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  85:    */     }
/*  86:133 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String eliminar()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:143 */       this.servicioCanal.eliminar(this.canal);
/*  94:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  99:147 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 100:    */     }
/* 101:149 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String cargarDatos()
/* 105:    */   {
/* 106:158 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String limpiar()
/* 110:    */   {
/* 111:167 */     crearCanal();
/* 112:168 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Canal getCanal()
/* 116:    */   {
/* 117:185 */     if (this.canal == null) {
/* 118:186 */       crearCanal();
/* 119:    */     }
/* 120:188 */     return this.canal;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setCanal(Canal canal)
/* 124:    */   {
/* 125:198 */     this.canal = canal;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public LazyDataModel<Canal> getListaCanal()
/* 129:    */   {
/* 130:207 */     return this.listaCanal;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setListaCanal(LazyDataModel<Canal> listaCanal)
/* 134:    */   {
/* 135:217 */     this.listaCanal = listaCanal;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public DataTable getDtCanal()
/* 139:    */   {
/* 140:226 */     return this.dtCanal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setDtCanal(DataTable dtCanal)
/* 144:    */   {
/* 145:236 */     this.dtCanal = dtCanal;
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.CanalBean
 * JD-Core Version:    0.7.0.1
 */