/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TareaProgramadaEnum;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Pattern;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="tarea_programada", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "expresion_tiempo", "tarea_programada_enum"})})
/*  23:    */ public class TareaProgramada
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="tarea_programada", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tarea_programada")
/*  31:    */   @Column(name="id_tarea_programada")
/*  32:    */   private int idTareaProgramada;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  37:    */   private Sucursal sucursal;
/*  38:    */   @Enumerated(EnumType.ORDINAL)
/*  39:    */   @Column(name="tarea_programada_enum", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private TareaProgramadaEnum tareaProgramadaEnum;
/*  42:    */   @Pattern(regexp="^(((\\?)|(\\*)|(\\d+(((\\d*)|([,|\\-]\\d+)+|(/\\d+)))))[\\s]*){6,7}$", message="Campo invalido")
/*  43:    */   @Column(name="expresion_tiempo", length=50, nullable=false)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=2, max=50)
/*  46:    */   private String expresionTiempo;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @Column(name="descripcion", length=200)
/*  52:    */   @Size(max=200)
/*  53:    */   private String descripcion;
/*  54:    */   
/*  55:    */   public int getId()
/*  56:    */   {
/*  57: 95 */     return this.idTareaProgramada;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdTareaProgramada()
/*  61:    */   {
/*  62:102 */     return this.idTareaProgramada;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdTareaProgramada(int idTareaProgramada)
/*  66:    */   {
/*  67:106 */     this.idTareaProgramada = idTareaProgramada;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72:110 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77:114 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Sucursal getSucursal()
/*  81:    */   {
/*  82:118 */     return this.sucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setSucursal(Sucursal sucursal)
/*  86:    */   {
/*  87:122 */     this.sucursal = sucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getExpresionTiempo()
/*  91:    */   {
/*  92:126 */     return this.expresionTiempo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setExpresionTiempo(String expresionTiempo)
/*  96:    */   {
/*  97:130 */     this.expresionTiempo = expresionTiempo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getDescripcion()
/* 101:    */   {
/* 102:134 */     return this.descripcion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setDescripcion(String descripcion)
/* 106:    */   {
/* 107:138 */     this.descripcion = descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isActivo()
/* 111:    */   {
/* 112:142 */     return this.activo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setActivo(boolean activo)
/* 116:    */   {
/* 117:146 */     this.activo = activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isPredeterminado()
/* 121:    */   {
/* 122:150 */     return this.predeterminado;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setPredeterminado(boolean predeterminado)
/* 126:    */   {
/* 127:154 */     this.predeterminado = predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public TareaProgramadaEnum getTareaProgramadaEnum()
/* 131:    */   {
/* 132:158 */     return this.tareaProgramadaEnum;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setTareaProgramadaEnum(TareaProgramadaEnum tareaProgramadaEnum)
/* 136:    */   {
/* 137:162 */     this.tareaProgramadaEnum = tareaProgramadaEnum;
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TareaProgramada
 * JD-Core Version:    0.7.0.1
 */