/*   1:    */ package com.asinfo.as2.entities.presupuesto;
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
/*  12:    */ import javax.validation.constraints.Max;
/*  13:    */ import javax.validation.constraints.Min;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="nivel_partida_presupuestaria", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  19:    */ public class NivelPartidaPresupuestaria
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 2888954884689730638L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="nivel_partida_presupuestaria", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="nivel_partida_presupuestaria")
/*  27:    */   @Column(name="id_nivel_partida_presupuestaria")
/*  28:    */   private int idNivelPartidaPresupuestaria;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="longitud", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private int longitud;
/*  36:    */   @Column(name="codigo", nullable=false)
/*  37:    */   @Min(1L)
/*  38:    */   @Max(9L)
/*  39:    */   private int codigo;
/*  40:    */   @Column(name="nombre", length=50, nullable=false)
/*  41:    */   @Size(min=2, max=50)
/*  42:    */   @NotNull
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   
/*  52:    */   public NivelPartidaPresupuestaria() {}
/*  53:    */   
/*  54:    */   public NivelPartidaPresupuestaria(int idNivelPartidaPresupuestaria, String nombre, int codigo)
/*  55:    */   {
/*  56: 90 */     this.idNivelPartidaPresupuestaria = idNivelPartidaPresupuestaria;
/*  57: 91 */     this.nombre = nombre;
/*  58: 92 */     this.codigo = codigo;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdNivelPartidaPresupuestaria()
/*  62:    */   {
/*  63:101 */     return this.idNivelPartidaPresupuestaria;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdNivelPartidaPresupuestaria(int idNivelPartidaPresupuestaria)
/*  67:    */   {
/*  68:111 */     this.idNivelPartidaPresupuestaria = idNivelPartidaPresupuestaria;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdOrganizacion()
/*  72:    */   {
/*  73:120 */     return this.idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdOrganizacion(int idOrganizacion)
/*  77:    */   {
/*  78:130 */     this.idOrganizacion = idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdSucursal()
/*  82:    */   {
/*  83:139 */     return this.idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdSucursal(int idSucursal)
/*  87:    */   {
/*  88:149 */     this.idSucursal = idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getLongitud()
/*  92:    */   {
/*  93:158 */     return this.longitud;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setLongitud(int longitud)
/*  97:    */   {
/*  98:168 */     this.longitud = longitud;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getDescripcion()
/* 102:    */   {
/* 103:177 */     return this.descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setDescripcion(String descripcion)
/* 107:    */   {
/* 108:187 */     this.descripcion = descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isActivo()
/* 112:    */   {
/* 113:196 */     return this.activo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setActivo(boolean activo)
/* 117:    */   {
/* 118:206 */     this.activo = activo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isPredeterminado()
/* 122:    */   {
/* 123:215 */     return this.predeterminado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPredeterminado(boolean predeterminado)
/* 127:    */   {
/* 128:225 */     this.predeterminado = predeterminado;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getCodigo()
/* 132:    */   {
/* 133:229 */     return this.codigo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setCodigo(int codigo)
/* 137:    */   {
/* 138:233 */     this.codigo = codigo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getNombre()
/* 142:    */   {
/* 143:237 */     return this.nombre;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setNombre(String nombre)
/* 147:    */   {
/* 148:241 */     this.nombre = nombre;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getId()
/* 152:    */   {
/* 153:246 */     return this.idNivelPartidaPresupuestaria;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria
 * JD-Core Version:    0.7.0.1
 */