/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.NotNull;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="ibp_capacidad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  17:    */ public class IBPCapacidad
/*  18:    */   extends EntidadBase
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="ibp_capacidad", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ibp_capacidad")
/*  25:    */   @Column(name="id_ibp_capacidad", unique=true, nullable=false)
/*  26:    */   private int idIBPCapacidad;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=6)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=6, max=6)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", nullable=false, length=100)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=1, max=100)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", nullable=true, length=300)
/*  40:    */   @Size(max=300)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   @NotNull
/*  44: 65 */   private boolean activo = true;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private boolean predeterminado;
/*  48:    */   
/*  49:    */   public IBPCapacidad() {}
/*  50:    */   
/*  51:    */   public IBPCapacidad(int idIBPCapacidad, String codigo, String nombre)
/*  52:    */   {
/*  53: 83 */     this.idIBPCapacidad = idIBPCapacidad;
/*  54: 84 */     this.codigo = codigo;
/*  55: 85 */     this.nombre = nombre;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getId()
/*  59:    */   {
/*  60: 90 */     return this.idIBPCapacidad;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdIBPCapacidad()
/*  64:    */   {
/*  65: 99 */     return this.idIBPCapacidad;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdIBPCapacidad(int idIBPCapacidad)
/*  69:    */   {
/*  70:109 */     this.idIBPCapacidad = idIBPCapacidad;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:118 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:128 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdSucursal()
/*  84:    */   {
/*  85:137 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(int idSucursal)
/*  89:    */   {
/*  90:147 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getCodigo()
/*  94:    */   {
/*  95:156 */     return this.codigo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setCodigo(String codigo)
/*  99:    */   {
/* 100:166 */     this.codigo = codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getNombre()
/* 104:    */   {
/* 105:175 */     return this.nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setNombre(String nombre)
/* 109:    */   {
/* 110:185 */     this.nombre = nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getDescripcion()
/* 114:    */   {
/* 115:194 */     return this.descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setDescripcion(String descripcion)
/* 119:    */   {
/* 120:204 */     this.descripcion = descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isActivo()
/* 124:    */   {
/* 125:213 */     return this.activo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setActivo(boolean activo)
/* 129:    */   {
/* 130:223 */     this.activo = activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isPredeterminado()
/* 134:    */   {
/* 135:232 */     return this.predeterminado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setPredeterminado(boolean predeterminado)
/* 139:    */   {
/* 140:242 */     this.predeterminado = predeterminado;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.IBPCapacidad
 * JD-Core Version:    0.7.0.1
 */