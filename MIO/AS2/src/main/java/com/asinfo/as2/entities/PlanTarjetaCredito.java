/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="plan_tarjeta_credito", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo", "id_organizacion"})})
/*  15:    */ public class PlanTarjetaCredito
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="plan_tarjeta_credito", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plan_tarjeta_credito")
/*  22:    */   @Column(name="id_plan_tarjeta_credito")
/*  23:    */   private int idPlanTarjetaCredito;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", length=10, nullable=false)
/*  29:    */   @NotNull
/*  30:    */   @Size(max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", length=50, nullable=false)
/*  33:    */   @NotNull
/*  34:    */   @Size(max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private boolean predeterminado;
/*  45:    */   @Column(name="indicador_con_interes", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private boolean indicadorConInteres;
/*  48:    */   
/*  49:    */   public int getId()
/*  50:    */   {
/*  51: 74 */     return this.idPlanTarjetaCredito;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIdPlanTarjetaCredito()
/*  55:    */   {
/*  56: 78 */     return this.idPlanTarjetaCredito;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdPlanTarjetaCredito(int idPlanTarjetaCredito)
/*  60:    */   {
/*  61: 82 */     this.idPlanTarjetaCredito = idPlanTarjetaCredito;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdOrganizacion()
/*  65:    */   {
/*  66: 86 */     return this.idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71: 90 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdSucursal()
/*  75:    */   {
/*  76: 94 */     return this.idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdSucursal(int idSucursal)
/*  80:    */   {
/*  81: 98 */     this.idSucursal = idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getCodigo()
/*  85:    */   {
/*  86:102 */     return this.codigo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setCodigo(String codigo)
/*  90:    */   {
/*  91:106 */     this.codigo = codigo;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getNombre()
/*  95:    */   {
/*  96:110 */     return this.nombre;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setNombre(String nombre)
/* 100:    */   {
/* 101:114 */     this.nombre = nombre;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getDescripcion()
/* 105:    */   {
/* 106:118 */     return this.descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDescripcion(String descripcion)
/* 110:    */   {
/* 111:122 */     this.descripcion = descripcion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isActivo()
/* 115:    */   {
/* 116:126 */     return this.activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setActivo(boolean activo)
/* 120:    */   {
/* 121:130 */     this.activo = activo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isPredeterminado()
/* 125:    */   {
/* 126:134 */     return this.predeterminado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPredeterminado(boolean predeterminado)
/* 130:    */   {
/* 131:138 */     this.predeterminado = predeterminado;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String toString()
/* 135:    */   {
/* 136:144 */     return this.nombre;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isIndicadorConInteres()
/* 140:    */   {
/* 141:148 */     return this.indicadorConInteres;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setIndicadorConInteres(boolean indicadorConInteres)
/* 145:    */   {
/* 146:152 */     this.indicadorConInteres = indicadorConInteres;
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PlanTarjetaCredito
 * JD-Core Version:    0.7.0.1
 */