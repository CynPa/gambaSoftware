/*   1:    */ package com.asinfo.as2.entities.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*   6:    */ import com.asinfo.as2.util.seguridad.AuthorizationPermission;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.JoinTable;
/*  17:    */ import javax.persistence.ManyToMany;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="permiso")
/*  25:    */ public class EntidadPermiso
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="permiso", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="permiso")
/*  32:    */   @Column(name="id_permiso", unique=true, nullable=false)
/*  33:    */   private int idPermiso;
/*  34:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  35:    */   @JoinColumn(name="id_rol")
/*  36:    */   private EntidadRol entidadRol;
/*  37:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  38:    */   @JoinColumn(name="id_proceso_organizacion")
/*  39:    */   private ProcesoOrganizacion procesoOrganizacion;
/*  40:    */   @ManyToMany(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE})
/*  41:    */   @JoinTable(name="permiso_accion", joinColumns={@JoinColumn(name="id_permiso")}, inverseJoinColumns={@JoinColumn(name="id_accion")})
/*  42:    */   private List<EntidadAccion> listaAccion;
/*  43:    */   @Column(name="acciones", nullable=true)
/*  44:    */   private String acciones;
/*  45:    */   @Transient
/*  46:    */   private String accionesSiglas;
/*  47:    */   
/*  48:    */   public EntidadPermiso() {}
/*  49:    */   
/*  50:    */   public EntidadPermiso(EntidadRol entidadRol, ProcesoOrganizacion procesoOrganizacion)
/*  51:    */   {
/*  52: 91 */     this.entidadRol = entidadRol;
/*  53: 92 */     this.procesoOrganizacion = procesoOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getId()
/*  57:    */   {
/*  58: 97 */     return this.idPermiso;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public AuthorizationPermission toPermision()
/*  62:    */   {
/*  63:101 */     return new AuthorizationPermission(this.procesoOrganizacion.getEntidadProceso().getId(), this.procesoOrganizacion.getEntidadProceso().getViewId(), this.procesoOrganizacion.getEntidadProceso().getViewName(), getAcciones(), this.procesoOrganizacion.getOrganizacion().getIdOrganizacion());
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdPermiso()
/*  67:    */   {
/*  68:110 */     return this.idPermiso;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdPermiso(int idPermiso)
/*  72:    */   {
/*  73:120 */     this.idPermiso = idPermiso;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public List<EntidadAccion> getListaAccion()
/*  77:    */   {
/*  78:129 */     if (this.listaAccion == null) {
/*  79:130 */       this.listaAccion = new ArrayList();
/*  80:    */     }
/*  81:132 */     return this.listaAccion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setListaAccion(List<EntidadAccion> listaAccion)
/*  85:    */   {
/*  86:142 */     this.listaAccion = listaAccion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public EntidadRol getEntidadRol()
/*  90:    */   {
/*  91:170 */     return this.entidadRol;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setEntidadRol(EntidadRol entidadRol)
/*  95:    */   {
/*  96:180 */     this.entidadRol = entidadRol;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getAccionesSiglas()
/* 100:    */   {
/* 101:184 */     this.accionesSiglas = "(";
/* 102:185 */     boolean primero = true;
/* 103:186 */     for (EntidadAccion accion : this.listaAccion)
/* 104:    */     {
/* 105:187 */       if (!primero) {
/* 106:188 */         this.accionesSiglas += ",";
/* 107:    */       }
/* 108:190 */       this.accionesSiglas += accion.getNombre().charAt(0);
/* 109:191 */       primero = false;
/* 110:    */     }
/* 111:193 */     this.accionesSiglas += ")";
/* 112:194 */     return this.accionesSiglas;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setAccionesSiglas(String accionesSiglas)
/* 116:    */   {
/* 117:198 */     this.accionesSiglas = accionesSiglas;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public ProcesoOrganizacion getProcesoOrganizacion()
/* 121:    */   {
/* 122:202 */     return this.procesoOrganizacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setProcesoOrganizacion(ProcesoOrganizacion procesoOrganizacion)
/* 126:    */   {
/* 127:206 */     this.procesoOrganizacion = procesoOrganizacion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getAcciones()
/* 131:    */   {
/* 132:214 */     if ((this.acciones == null) && (this.listaAccion != null))
/* 133:    */     {
/* 134:215 */       this.acciones = "";
/* 135:216 */       for (EntidadAccion a : this.listaAccion) {
/* 136:217 */         this.acciones += a.getNombre();
/* 137:    */       }
/* 138:    */     }
/* 139:221 */     return this.acciones;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setAcciones(String acciones)
/* 143:    */   {
/* 144:228 */     this.acciones = acciones;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.seguridad.EntidadPermiso
 * JD-Core Version:    0.7.0.1
 */