/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.MotivoAjusteUnidadManejo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class MotivoAjusteUnidadManejoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -8204372454439685260L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<MotivoAjusteUnidadManejo> servicioMotivoAjusteUnidadManejo;
/*  32:    */   private MotivoAjusteUnidadManejo motivoAjusteUnidadManejo;
/*  33:    */   private LazyDataModel<MotivoAjusteUnidadManejo> listaMotivoAjusteUnidadManejo;
/*  34:    */   private DataTable dtMotivoAjusteUnidadManejo;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 53 */     this.listaMotivoAjusteUnidadManejo = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<MotivoAjusteUnidadManejo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 61 */         List<MotivoAjusteUnidadManejo> lista = new ArrayList();
/*  46: 62 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 64 */         MotivoAjusteUnidadManejoBean.this.agregarFiltroOrganizacion(filters);
/*  49:    */         
/*  50: 66 */         lista = MotivoAjusteUnidadManejoBean.this.servicioMotivoAjusteUnidadManejo.obtenerListaPorPagina(MotivoAjusteUnidadManejo.class, startIndex, pageSize, sortField, ordenar, filters);
/*  51:    */         
/*  52: 68 */         MotivoAjusteUnidadManejoBean.this.listaMotivoAjusteUnidadManejo
/*  53: 69 */           .setRowCount(MotivoAjusteUnidadManejoBean.this.servicioMotivoAjusteUnidadManejo.contarPorCriterio(MotivoAjusteUnidadManejo.class, filters));
/*  54:    */         
/*  55: 71 */         return lista;
/*  56:    */       }
/*  57:    */     };
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String editar()
/*  61:    */   {
/*  62: 79 */     if ((this.motivoAjusteUnidadManejo != null) && (this.motivoAjusteUnidadManejo.getId() != 0)) {
/*  63: 80 */       setEditado(true);
/*  64:    */     } else {
/*  65: 82 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  66:    */     }
/*  67: 84 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String guardar()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74: 90 */       this.servicioMotivoAjusteUnidadManejo.guardarValidar(getMotivoAjusteUnidadManejo());
/*  75: 91 */       limpiar();
/*  76: 92 */       setEditado(false);
/*  77: 93 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  78:    */     }
/*  79:    */     catch (AS2Exception e)
/*  80:    */     {
/*  81: 95 */       JsfUtil.addErrorMessage(e, "");
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85: 97 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86: 98 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:100 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:105 */     if ((this.motivoAjusteUnidadManejo != null) && (this.motivoAjusteUnidadManejo.getId() != 0)) {
/*  94:    */       try
/*  95:    */       {
/*  96:107 */         this.servicioMotivoAjusteUnidadManejo.eliminar(this.motivoAjusteUnidadManejo);
/*  97:108 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  98:109 */         limpiar();
/*  99:    */       }
/* 100:    */       catch (Exception e)
/* 101:    */       {
/* 102:111 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 103:112 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 104:    */       }
/* 105:    */     } else {
/* 106:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 107:    */     }
/* 108:117 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String limpiar()
/* 112:    */   {
/* 113:122 */     crearMotivoAjusteUnidadManejo();
/* 114:123 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String cargarDatos()
/* 118:    */   {
/* 119:128 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void crearMotivoAjusteUnidadManejo()
/* 123:    */   {
/* 124:132 */     this.motivoAjusteUnidadManejo = new MotivoAjusteUnidadManejo();
/* 125:133 */     this.motivoAjusteUnidadManejo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 126:134 */     this.motivoAjusteUnidadManejo.setIdSucursal(AppUtil.getSucursal().getId());
/* 127:135 */     this.motivoAjusteUnidadManejo.setActivo(true);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public MotivoAjusteUnidadManejo getMotivoAjusteUnidadManejo()
/* 131:    */   {
/* 132:142 */     return this.motivoAjusteUnidadManejo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setMotivoAjusteUnidadManejo(MotivoAjusteUnidadManejo motivoAjusteUnidadManejo)
/* 136:    */   {
/* 137:150 */     this.motivoAjusteUnidadManejo = motivoAjusteUnidadManejo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public LazyDataModel<MotivoAjusteUnidadManejo> getListaMotivoAjusteUnidadManejo()
/* 141:    */   {
/* 142:157 */     return this.listaMotivoAjusteUnidadManejo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setListaMotivoAjusteUnidadManejo(LazyDataModel<MotivoAjusteUnidadManejo> listaMotivoAjusteUnidadManejo)
/* 146:    */   {
/* 147:165 */     this.listaMotivoAjusteUnidadManejo = listaMotivoAjusteUnidadManejo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public DataTable getDtMotivoAjusteUnidadManejo()
/* 151:    */   {
/* 152:172 */     return this.dtMotivoAjusteUnidadManejo;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setDtMotivoAjusteUnidadManejo(DataTable dtMotivoAjusteUnidadManejo)
/* 156:    */   {
/* 157:180 */     this.dtMotivoAjusteUnidadManejo = dtMotivoAjusteUnidadManejo;
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.MotivoAjusteUnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */