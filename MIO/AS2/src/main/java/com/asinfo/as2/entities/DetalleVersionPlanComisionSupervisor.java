/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="detalle_version_plan_comision_supervisor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_version_plan_comision", "id_agente_comercial", "id_empresa"})})
/*  19:    */ public class DetalleVersionPlanComisionSupervisor
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="detalle_version_plan_comision_supervisor", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_version_plan_comision_supervisor")
/*  26:    */   @Column(name="id_detalle_version_plan_comision_supervisor")
/*  27:    */   private int idDetalleVersionPlanComisionSupervisor;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="descripcion", nullable=true, length=200)
/*  33:    */   @Size(max=200)
/*  34:    */   private String descripcion;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_version_plan_comision", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private VersionPlanComision versionPlanComision;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_agente_comercial", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private EntidadUsuario agenteComercial;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private Empresa empresa;
/*  47:    */   
/*  48:    */   public int getId()
/*  49:    */   {
/*  50: 97 */     return this.idDetalleVersionPlanComisionSupervisor;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdDetalleVersionPlanComisionSupervisor()
/*  54:    */   {
/*  55:101 */     return this.idDetalleVersionPlanComisionSupervisor;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdDetalleVersionPlanComisionSupervisor(int idDetalleVersionPlanComisionSupervisor)
/*  59:    */   {
/*  60:105 */     this.idDetalleVersionPlanComisionSupervisor = idDetalleVersionPlanComisionSupervisor;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdOrganizacion()
/*  64:    */   {
/*  65:109 */     return this.idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdOrganizacion(int idOrganizacion)
/*  69:    */   {
/*  70:113 */     this.idOrganizacion = idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdSucursal()
/*  74:    */   {
/*  75:117 */     return this.idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdSucursal(int idSucursal)
/*  79:    */   {
/*  80:121 */     this.idSucursal = idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getDescripcion()
/*  84:    */   {
/*  85:125 */     return this.descripcion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setDescripcion(String descripcion)
/*  89:    */   {
/*  90:129 */     this.descripcion = descripcion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public VersionPlanComision getVersionPlanComision()
/*  94:    */   {
/*  95:133 */     return this.versionPlanComision;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setVersionPlanComision(VersionPlanComision versionPlanComision)
/*  99:    */   {
/* 100:137 */     this.versionPlanComision = versionPlanComision;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public EntidadUsuario getAgenteComercial()
/* 104:    */   {
/* 105:141 */     return this.agenteComercial;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 109:    */   {
/* 110:145 */     this.agenteComercial = agenteComercial;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Empresa getEmpresa()
/* 114:    */   {
/* 115:149 */     return this.empresa;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setEmpresa(Empresa empresa)
/* 119:    */   {
/* 120:153 */     this.empresa = empresa;
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleVersionPlanComisionSupervisor
 * JD-Core Version:    0.7.0.1
 */